package ru.nickmiller.tinkofffintech.ui.courses

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import ru.nickmiller.tinkofffintech.data.entity.course.Course
import ru.nickmiller.tinkofffintech.data.repository.CoursesRepository
import ru.nickmiller.tinkofffintech.ui.base.DataViewModel


class CoursesViewModel(val repo: CoursesRepository) : DataViewModel<List<Course>>() {

    init {
        getCourses()
    }

    fun getCourses(refresh: Boolean = false) {
        disposable = repo.getCourses(refresh)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { if (!refresh) loadingObservable.postValue(true) }
            .subscribe(
                { postData(it) },
                { postError(it) }
            )
    }
}