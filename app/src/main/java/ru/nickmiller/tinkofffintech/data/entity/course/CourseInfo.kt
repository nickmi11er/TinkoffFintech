package ru.nickmiller.tinkofffintech.data.entity.course

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import ru.nickmiller.tinkofffintech.data.entity.ApiResponse


@Parcelize
data class CourseInfo(
    @SerializedName("grades") val grades: List<Grade>?,
    @SerializedName("grouped_tasks") val groupedTasks: List<List<GroupedTask>>?,
    @SerializedName("id") val id: Int?,
//    @SerializedName("mentor") val mentor: Any,
//    @SerializedName("mentor_id") val mentorId: Any,
    @SerializedName("name") val name: String?
): ApiResponse(), Parcelable

@Parcelize
data class GroupedTask(
    @SerializedName("short_name") val shortName: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("id") val id: Int?,
    @SerializedName("max_score") val maxScore: Double?,
    @SerializedName("contest__status") val contestStatus: String?
): Parcelable

@Parcelize
data class Grade(
    @SerializedName("grades") val gradeMarks: List<GradeMark>?,
    @SerializedName("group_id") val groupId: Int?,
    @SerializedName("student") val student: String?,
    @SerializedName("student_id") val studentId: Int?
): Parcelable

@Parcelize
data class GradeMark(
    @SerializedName("mark") val mark: Double?,
    @SerializedName("status") val status: String?,
    @SerializedName("task_type") val taskType: String?
) : Parcelable
 
 
