package ru.nickmiller.tinkofffintech.data.entity.course

import com.google.gson.annotations.SerializedName


data class CoursesResponse(
    @SerializedName("active") val active: List<Course>?,
    @SerializedName("archive") val archive: List<Course>?,
    @SerializedName("courses") val courses: List<Course>?
)

data class Course(
    @SerializedName("is_teacher") val isTeacher: Boolean?,
    @SerializedName("title") val title: String?,
    @SerializedName("pending_tasks") val pendingTasks: PendingTasks?,
    @SerializedName("status") val status: String?,
    @SerializedName("event_date_start") val eventDateStart: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("diploma_url") val diplomaUrl: String?
)

data class PendingTasks(
    @SerializedName("lecture_tests") val lectureTests: List<LectureTest>?,
    @SerializedName("tasks") val tasks: List<Any>?
)

data class LectureTest(
    @SerializedName("id") val id: Number?,
    @SerializedName("title") val title: String?,
    @SerializedName("contest_status") val contestStatus: ContestStatus?,
    @SerializedName("contest_url") val contestUrl: String?
)

data class ContestStatus(
    @SerializedName("time_left") val timeLeft: String?,
    @SerializedName("status") val status: String?
)

 
 
