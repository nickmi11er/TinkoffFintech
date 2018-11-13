package ru.nickmiller.tinkofffintech.ui.events

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.utils.find

class EventDetailsActivity : AppCompatActivity() {
    val btnBack by lazy { find<View>(R.id.btn_back) }
    val eventTitle by lazy { find<TextView>(R.id.event_title) }
    val eventType by lazy { find<TextView>(R.id.event_type) }
    val eventImage by lazy { find<ImageView>(R.id.event_img) }
    val eventDescription by lazy { find<TextView>(R.id.event_description) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        btnBack.setOnClickListener { onBackPressed() }

        val event = intent.getParcelableExtra<Event>(EVENT)
        if (event.eventInfo != null) {
            setUpEvent(event)
        } else {

        }
    }


    private fun setUpEvent(event: Event) {
        eventTitle.text = event.title
        eventDescription.text = event.description
        event.eventInfo?.data?.eventImage?.let {
            val url = it.substring(5, it.length - 2)
            Glide.with(this).load("https://fintech.tinkoff.ru$url").into(eventImage)
        }
        eventType.text = event.eventType?.name ?: "Мероприятие"
        val color = getEventTypeColor(event.eventType?.name)
        DrawableCompat.setTint(eventType.background, ContextCompat.getColor(this, color))
    }

    companion object {
        private val EVENT = "event"

        fun start(context: Context?, event: Event) {
            val intent = Intent(context, EventDetailsActivity::class.java)
            intent.putExtra(EVENT, event)
            context?.startActivity(intent)
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
