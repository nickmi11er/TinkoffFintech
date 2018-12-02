package ru.nickmiller.tinkofffintech.data.cache

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import ru.nickmiller.tinkofffintech.data.cache.dao.CoursesDao
import ru.nickmiller.tinkofffintech.data.cache.dao.EventsDao
import ru.nickmiller.tinkofffintech.data.cache.dao.ProfileDao
import ru.nickmiller.tinkofffintech.data.entity.course.Course
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.data.entity.profile.Profile


@Database(entities = [Profile::class, Event::class, Course::class], version = 4)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    abstract fun eventsDao(): EventsDao

    abstract fun coursesDao(): CoursesDao

}