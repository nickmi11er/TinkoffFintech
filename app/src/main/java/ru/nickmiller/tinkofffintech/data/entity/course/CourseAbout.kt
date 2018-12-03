package ru.nickmiller.tinkofffintech.data.entity.course

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.nickmiller.tinkofffintech.data.entity.ApiResponse

@Parcelize
data class CourseAbout(
    val delta: Delta,
    val html: String,
    val title: String
) : ApiResponse(), Parcelable


@Parcelize
data class Delta(
    val ops: List<Op>
) : Parcelable


data class Op(
    val attributes: Attributes?,
    val insert: Any?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Attributes::class.java.classLoader),
        parcel.readString()
    )

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeParcelable(attributes, flags)
        dest.writeString(if (insert is String) insert else "")
    }

    override fun describeContents() = 0

    companion object CREATOR : Parcelable.Creator<Op> {
        override fun createFromParcel(parcel: Parcel): Op {
            return Op(parcel)
        }

        override fun newArray(size: Int): Array<Op?> {
            return arrayOfNulls(size)
        }
    }

}


@Parcelize
data class Attributes(
    val header: Int
) : Parcelable
 
 
