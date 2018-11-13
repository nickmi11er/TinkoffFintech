package ru.nickmiller.tinkofffintech.ui.events

import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder
import ru.nickmiller.tinkofffintech.utils.find
import ru.nickmiller.tinkofffintech.utils.sdfFintech
import java.util.*

abstract class EventsViewHolder(itemView: View) : BaseViewHolder<Event>(itemView) {
    val eventType by lazy { find<TextView>(R.id.event_type) }
    val eventTitle by lazy { find<TextView>(R.id.event_title) }
    val eventImg by lazy { find<ImageView>(R.id.event_img) }
    val eventDate by lazy { find<TextView>(R.id.event_date) }
}

class ArchiveEventsViewHolder(itemView: View, val large: Boolean = false) : EventsViewHolder(itemView) {
    val eventPlace by lazy { find<TextView>(R.id.event_place) }

    override fun bind(item: Event) {
        eventType.text = item.eventType?.name ?: "Мероприятие"
        eventTitle.text = item.title
        if (large) {
            if (!item.place.isNullOrEmpty()) {
                eventPlace.visibility = View.VISIBLE
                eventPlace.text = item.place
            }

            eventDate.visibility = View.VISIBLE
            eventDate.text = getDateGap(item.dateStart, item.dateEnd)
        }
        eventImg.setImageDrawable(ContextCompat.getDrawable(itemView.context, getEventIcon(item.eventType?.name ?: "")))
    }

    private fun getEventIcon(eventType: String): Int =
        when (eventType) {
            "Финтех Школа" -> R.drawable.ic_event_ft_school
            "Стажировка" -> R.drawable.ic_event_intership
            "Курсы для школьников" -> R.drawable.ic_event_school
            "Спецкурс" -> R.drawable.ic_event_spec_cource
            else -> R.drawable.ic_event
        }
}

class ActualEventsViewHolder(itemView: View, val large: Boolean = false) : EventsViewHolder(itemView) {
    val description by lazy { find<TextView>(R.id.event_description) }

    override fun bind(item: Event) {
        if (large) {
            if (item.description?.isEmpty() != false) description.visibility = View.GONE
            else description.text = item.description
        }

        eventType.text = item.eventType?.name ?: "Мероприятие"
        val color = getEventTypeColor(item.eventType?.name)
        DrawableCompat.setTint(eventType.background, ContextCompat.getColor(itemView.context, color))

        eventDate.text = getDateGap(item.dateStart, item.dateEnd)
        eventTitle.text = item.title
        item.eventInfo?.data?.eventImage?.let {
            val url = it.substring(5, it.length - 2)
            Glide.with(itemView).load("https://fintech.tinkoff.ru$url").into(eventImg)
        }
    }

    private fun getEventTypeColor(eventType: String?) =
        when (eventType) {
            "Финтех Школа" -> R.color.colorRedSoft
            "Стажировка" -> R.color.colorOrangeSoft
            "Курсы для школьников" -> R.color.colorVioletSoft
            "Спецкурс" -> R.color.colorGreenSoft
            else -> R.color.colorBlueSoft
        }
}


private fun getDateGap(dtStart: String?, dtEnd: String?): String {
    if (dtStart == null || dtEnd == null) {
        return ""
    }

    Calendar.getInstance().time = sdfFintech.parse(dtStart)
    val yearStart = Calendar.getInstance().get(Calendar.YEAR)
    val monthStart = Calendar.getInstance().get(Calendar.MONTH)
    val monthStartName = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("ru"))

    Calendar.getInstance().time = sdfFintech.parse(dtEnd)
    val yearEnd = Calendar.getInstance().get(Calendar.YEAR)
    val monthEnd = Calendar.getInstance().get(Calendar.MONTH)
    val monthEndName = Calendar.getInstance().getDisplayName(Calendar.MONTH, Calendar.SHORT, Locale("ru"))

    return if (yearStart == yearEnd) {
        if (monthStart == monthEnd) "$monthStartName $yearStart г."
        else "$monthStartName - $monthEndName $yearStart г."
    } else {
        "$monthStartName $yearStart г. - $monthEndName $yearEnd г."
    }
}
 
 
