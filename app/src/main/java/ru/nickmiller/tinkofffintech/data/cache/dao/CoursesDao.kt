package ru.nickmiller.tinkofffintech.data.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import ru.nickmiller.tinkofffintech.data.entity.course.Course


@Dao
interface CoursesDao {

    @Query("SELECT * FROM courses")
    fun getAllCourses(): List<Course>?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveCourses(courses: List<Course>)

}