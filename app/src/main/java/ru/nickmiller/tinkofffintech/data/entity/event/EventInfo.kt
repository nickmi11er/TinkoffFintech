package ru.nickmiller.tinkofffintech.data.entity.event

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class EventInfo(
    @SerializedName("title") val title: String?,
    @SerializedName("data") val data: EventData?,
    @SerializedName("blocks") val blocks: List<Block>?,
    @SerializedName("event_state_title") val event_state_title: String?
) : Parcelable


@Parcelize
data class EventData(
    @SerializedName("header_background_style") val eventImage: String?
) : Parcelable


@Parcelize
data class Block(
    @SerializedName("angular_block_id") val blockId: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("data") val data: Data?
) : Parcelable


@Parcelize
data class Data(
    @SerializedName("type") val type: String?,
    @SerializedName("event_type_id") val eventTypeId: String?,
    val event_url: String?,
    @SerializedName("button_text") val buttonText: String?,
    @SerializedName("text") val text: String?,
    val action_link: String?,
    val registration: Boolean?,
    @SerializedName("subtitle") val subtitle: String?,
    @SerializedName("about") val about: String?,
    @SerializedName("title") val title: String?,
    val applied: Applied?,
    @SerializedName("children") val childrens: List<Children>?
) : Parcelable


@Parcelize
data class Children(
    @SerializedName("title") val title: String?,
    @SerializedName("angular_block_id") val blockId: String?,
    @SerializedName("data") val data: Data?
) : Parcelable


@Parcelize
data class Applied(
    val type: String?,
    val button_text: String?,
    val text: String?,
    val about: String?,
    val title: String?
) : Parcelable

 
