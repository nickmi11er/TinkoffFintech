package ru.nickmiller.tinkofffintech.data.repository

import io.reactivex.Observable
import io.reactivex.functions.Function3
import ru.nickmiller.tinkofffintech.ConnectionIsNotAvailable
import ru.nickmiller.tinkofffintech.EmptyDbTableException
import ru.nickmiller.tinkofffintech.data.Api
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.data.cache.AppDatabase
import ru.nickmiller.tinkofffintech.data.entity.course.Course
import ru.nickmiller.tinkofffintech.data.entity.course.CourseAbout
import ru.nickmiller.tinkofffintech.data.entity.course.CourseInfo
import ru.nickmiller.tinkofffintech.utils.NetworkUtil


class CoursesRepository(val api: Api, val db: AppDatabase, val networkUtil: NetworkUtil) {

    private fun courseInfoUrl(courseId: String) = "https://fintech.tinkoff.ru/api/course/$courseId/grades"
    private fun courseAboutUrl(courseId: String) = "https://fintech.tinkoff.ru/api/course/$courseId/about"


    fun getCourses(update: Boolean = false): Observable<Resource<List<Course>>> =
        if (update) getCoursesFromCloud()
        else {
            Observable.concat(
                getFromCache().filter { !it.data.isNullOrEmpty() },
                getCoursesFromCloud()
            )
        }


    fun getFromCache() = Observable.fromCallable {
        val courses = db.coursesDao().getAllCourses()
        if (courses != null) {
            Resource.success(courses)
        } else {
            Resource.error(EmptyDbTableException())
        }
    }

    fun getCoursesFromCloud() = networkUtil.isConnected()
        .flatMap { connected ->
            if (connected) {
                api.getCourses().flatMapIterable { it.courses }
                    .map { it.apply { userId = db.profileDao().getProfile()!!.id } }
                    .flatMap { course ->
                        Observable.zip(
                            Observable.just(course),
                            api.getCourseInfo(courseInfoUrl(course.url)),
                            api.getCourseAbout(courseAboutUrl(course.url)),
                            Function3<Course, List<CourseInfo>, CourseAbout, Course> { lCourse, courseInfo, courseAbout ->
                                lCourse.courseInfo = courseInfo
                                lCourse.courseAbout = courseAbout
                                lCourse
                            }
                        )
                    }
                    .toList()
                    .map {
                        db.coursesDao().saveCourses(it)
                        Resource.success(it)
                    }
                    .toObservable()
            } else {
                Observable.just(Resource.error(ConnectionIsNotAvailable()))
            }
        }


    fun getCourseInfo(courseId: String) = networkUtil.isConnected()
        .flatMap { connected ->
            if (connected) {
                api.getCourseInfo("https://fintech.tinkoff.ru/api/course/$courseId/grades")
                    .map { Resource.success(it) }
            } else {
                Observable.just(Resource.error(ConnectionIsNotAvailable()))
            }
        }
}