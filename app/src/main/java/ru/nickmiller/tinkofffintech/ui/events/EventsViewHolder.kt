package ru.nickmiller.tinkofffintech.ui.events

import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_events_actual_item_large.eventDescription
import kotlinx.android.synthetic.main.view_events_archive_item.*
import ru.nickmiller.tinkofffintech.BuildConfig
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.data.entity.event.getEventTypeColor
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder
import ru.nickmiller.tinkofffintech.utils.sdfFintech
import java.util.*

abstract class EventsViewHolder(itemView: View) : BaseViewHolder<Event>(itemView)

class ArchiveEventsViewHolder(itemView: View, val large: Boolean = false) : EventsViewHolder(itemView) {

    override fun bind(item: Event) {
        eventType.text = item.eventType?.name ?: itemView.context.getString(R.string.title_event_single)
        eventTitle.text = item.title
        if (large) {
            if (!item.place.isNullOrEmpty()) {
                eventPlace.visibility = View.VISIBLE
                eventPlace.text = item.place
            }

            eventDate.visibility = View.VISIBLE
            val dateGap = getDateGap(item.dateStart, item.dateEnd)
            eventDate.text = dateGap
        }
        eventImg.setImageDrawable(ContextCompat.getDrawable(itemView.context, getEventIcon(item.eventType?.name ?: "")))
    }

    private fun getEventIcon(eventType: String): Int {
        val context = itemView.context
        return when (eventType) {
            context.getString(R.string.title_fintech_school) -> R.drawable.ic_event_ft_school
            context.getString(R.string.title_internship) -> R.drawable.ic_event_intership
            context.getString(R.string.title_school_courses) -> R.drawable.ic_event_school
            context.getString(R.string.title_special_course) -> R.drawable.ic_event_spec_cource
            else -> R.drawable.ic_event
        }
    }
}

class ActualEventsViewHolder(itemView: View, val large: Boolean = false) : EventsViewHolder(itemView) {

    override val containerView = itemView

    override fun bind(item: Event) {
        if (large) {
            if (item.description?.isEmpty() != false) eventDescription.visibility = View.GONE
            else eventDescription.text = item.description
        }

        eventType.text = item.eventType?.name ?: itemView.context.getString(R.string.title_event_single)
        val color = item.getEventTypeColor()
        DrawableCompat.setTint(eventType.background, ContextCompat.getColor(itemView.context, color))

        val dateGap = getDateGap(item.dateStart, item.dateEnd)
        eventDate.text = dateGap
        eventTitle.text = item.title
        item.eventInfo?.data?.eventImage?.let {
            val url = it.substring(6, it.length - 2)
            Glide.with(itemView).load(BuildConfig.MAIN_URL + url).into(eventImg)
        }
    }
}


private fun getDateGap(dtStart: String?, dtEnd: String?): String {
    if (dtStart == null || dtEnd == null) {
        return ""
    }

    val cal = Calendar.getInstance()
    cal.time = sdfFintech.parse(dtStart)
    val yearStart = cal.get(Calendar.YEAR)
    val monthStart = cal.get(Calendar.MONTH)
    val monthStartName = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("ru"))

    cal.time = sdfFintech.parse(dtEnd)
    val yearEnd = cal.get(Calendar.YEAR)
    val monthEnd = cal.get(Calendar.MONTH)
    val monthEndName = cal.getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("ru"))

    return if (yearStart == yearEnd) {
        if (monthStart == monthEnd) "$monthStartName $yearStart г."
        else "$monthStartName - $monthEndName $yearStart г."
    } else {
        "$monthStartName $yearStart г. - $monthEndName $yearEnd г."
    }
}
 
 
