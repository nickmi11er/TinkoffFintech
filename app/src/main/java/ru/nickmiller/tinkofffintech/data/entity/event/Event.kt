package ru.nickmiller.tinkofffintech.data.entity.event

import com.google.gson.annotations.SerializedName

data class Events(
    @SerializedName("active") val active: List<Event>?,
    @SerializedName("archive") val archive: List<Event>?
)

data class Event(
    @SerializedName("title") val title: String?,
    @SerializedName("date_start") val dateStart: String?,
    @SerializedName("date_end") val dateEnd: String?,
    @SerializedName("event_type") val eventType: EventType?,
    @SerializedName("custom_date") val customDate: String?,
    @SerializedName("place") val place: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("url_external") val urlExternal: Boolean?,
    @SerializedName("display_button") val displayButton: Boolean?,
    @SerializedName("url_text") val urlText: String?,
    @SerializedName("description") val description: String?
)


data class EventType(
    @SerializedName("name") val name: String?,
    @SerializedName("color") val color: String?
)