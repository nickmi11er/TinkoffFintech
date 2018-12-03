package ru.nickmiller.tinkofffintech.data.entity.course

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


data class CoursesResponse(
    @SerializedName("active") val active: List<Course>?,
    @SerializedName("archive") val archive: List<Course>?,
    @SerializedName("courses") val courses: List<Course>?
)

@Entity(tableName = "courses")
@Parcelize
data class Course(
    @PrimaryKey @SerializedName("url") val url: String = "",
    @SerializedName("is_teacher") val isTeacher: Boolean?,
    @SerializedName("title") val title: String?,
    @SerializedName("pending_tasks") val pendingTasks: PendingTasks?,
    @SerializedName("status") val status: String?,
    @SerializedName("event_date_start") val eventDateStart: String?,
    @SerializedName("diploma_url") val diplomaUrl: String?,
    var courseInfo: List<CourseInfo>?,
    var courseAbout: CourseAbout?,
    var userId: Int?
) : Parcelable


@Parcelize
data class PendingTasks(
    @SerializedName("lecture_tests") val lectureTests: List<LectureTest>?
    //@SerializedName("tasks") val tasks: List<Any>?
) : Parcelable


@Parcelize
data class LectureTest(
    @SerializedName("id") val id: Number?,
    @SerializedName("title") val title: String?,
    @SerializedName("contest_status") val contestStatus: ContestStatus?,
    @SerializedName("contest_url") val contestUrl: String?
) : Parcelable


@Parcelize
data class ContestStatus(
    @SerializedName("time_left") val timeLeft: String?,
    @SerializedName("status") val status: String?
) : Parcelable
 
 
