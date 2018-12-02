package ru.nickmiller.tinkofffintech.data.cache

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.nickmiller.tinkofffintech.data.entity.course.CourseAbout
import ru.nickmiller.tinkofffintech.data.entity.course.CourseInfo
import ru.nickmiller.tinkofffintech.data.entity.course.PendingTasks
import ru.nickmiller.tinkofffintech.data.entity.event.EventInfo
import ru.nickmiller.tinkofffintech.data.entity.event.EventType


class Converters {
    @TypeConverter
    fun fromString(value: String): List<String> {
        val listType = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromArrayList(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun fromEventTypeString(value: String?): EventType? {
        val gson = Gson()
        return gson.fromJson(value, EventType::class.java)
    }

    @TypeConverter
    fun fromEventType(type: EventType?): String? {
        val gson = Gson()
        return gson.toJson(type)
    }

    @TypeConverter
    fun fromEventInfoString(value: String?): EventInfo? {
        val gson = Gson()
        return gson.fromJson(value, EventInfo::class.java)
    }

    @TypeConverter
    fun fromEventInfo(type: EventInfo?): String? {
        val gson = Gson()
        return gson.toJson(type)
    }

    @TypeConverter
    fun fromCourseAboutString(value: String?): CourseAbout? {
        val gson = Gson()
        return gson.fromJson(value, CourseAbout::class.java)
    }

    @TypeConverter
    fun fromCourseAbout(type: CourseAbout?): String? {
        val gson = Gson()
        return gson.toJson(type)
    }

    @TypeConverter
    fun fromCourseInfoListString(value: String?): List<CourseInfo>? {
        val listType = object : TypeToken<List<CourseInfo>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun fromCourseInfoList(type: List<CourseInfo>?): String? {
        val gson = Gson()
        return gson.toJson(type)
    }

    @TypeConverter
    fun fromPendingTasksString(value: String?): PendingTasks? {
        val gson = Gson()
        return gson.fromJson(value, PendingTasks::class.java)
    }

    @TypeConverter
    fun fromPendingTasks(type: PendingTasks?): String? {
        val gson = Gson()
        return gson.toJson(type)
    }
}