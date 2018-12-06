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
    @SerializedName("email") var email: String?,
    @SerializedName("first_name") var firstName: String?,
    @SerializedName("last_name") var lastName: String?,
    @SerializedName("middle_name") var middleName: String?,
    @SerializedName("birthday") var birthday: String?,
    @SerializedName("phone_mobile") var mobile: String?,
    @SerializedName("avatar") var avatar: String?,
    @SerializedName("is_client") var isClient: Boolean?,
    @SerializedName("skype_login") var skypeLogin: String?,
    @SerializedName("description") var description: String?,
    @SerializedName("region") var region: String?,
    @SerializedName("school") var school: String?,
    @SerializedName("school_graduation") var schoolGraduation: String?,
    @SerializedName("university") var university: String?,
    @SerializedName("faculty") var faculty: String?,
    @SerializedName("university_graduation") var universityGraduation: String?,
    @SerializedName("grade") var grade: String?,
    @SerializedName("department") var department: String?,
    @SerializedName("current_work") var currentWork: String?,
    @SerializedName("resume") var resume: String?,
    @SerializedName("notifications") var notifications: List<String>?,
    @SerializedName("admin") var admin: Boolean?,
    @SerializedName("t_shirt_size") var tShirtSize: String?,
    var lastUpdatedTime: Long = 0
) : ApiResponse(), Parcelable


data class UserResponse(
    @SerializedName("user") val user: Profile?,
    @SerializedName("status") val status: String?,
    @SerializedName("message") val message: String?
)