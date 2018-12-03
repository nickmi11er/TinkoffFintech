package ru.nickmiller.tinkofffintech.data.entity.profile

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import ru.nickmiller.tinkofffintech.data.entity.ApiResponse


@Entity
@Parcelize
data class Profile(
    @PrimaryKey @SerializedName("id") val id: Int?,
    @SerializedName("email") val email: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("middle_name") val middleName: String?,
    @SerializedName("birthday") val birthday: String?,
    @SerializedName("phone_mobile") val mobile: String?,
    @SerializedName("avatar") val avatar: String?,
    @SerializedName("is_client") val isClient: Boolean?,
    @SerializedName("skype_login") val skypeLogin: String?,
    @SerializedName("description") val description: String?,
    @SerializedName("region") val region: String?,
    @SerializedName("school") val school: String?,
    @SerializedName("school_graduation") val schoolGraduation: String?,
    @SerializedName("university") val university: String?,
    @SerializedName("faculty") val faculty: String?,
    @SerializedName("university_graduation") val universityGraduation: Int?,
    @SerializedName("grade") val grade: String?,
    @SerializedName("department") val department: String?,
    @SerializedName("current_work") val currentWork: String?,
    @SerializedName("resume") val resume: String?,
    @SerializedName("notifications") val notifications: List<String>?,
    @SerializedName("admin") val admin: Boolean?,
    @SerializedName("t_shirt_size") val tShirtSize: String?,
    var lastUpdatedTime: Long = 0
) : ApiResponse(), Parcelable


data class UserResponse(
    @SerializedName("user") val user: Profile?,
    @SerializedName("status") val status: String?,
    @SerializedName("message") val message: String?
)