package ru.nickmiller.tinkofffintech.ui.events.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v4.graphics.drawable.DrawableCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_event_details.*
import ru.nickmiller.tinkofffintech.BuildConfig
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.event.Children
import ru.nickmiller.tinkofffintech.data.entity.event.Data
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.data.entity.event.getEventTypeColor
import ru.nickmiller.tinkofffintech.utils.find

class EventDetailsActivity : AppCompatActivity() {
    private val REGISTRATION_BLOCK = "app-registration-block"
    private val CARD_TAG = "black"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        eventBlocksRecycler.isNestedScrollingEnabled = false

        val event = intent.getParcelableExtra<Event>(EVENT)
        setUpEvent(event)
    }


    private fun setUpEvent(event: Event) {
        val items = mutableListOf<Data>()
        event.eventInfo?.blocks?.forEach {
            if (it.blockId == REGISTRATION_BLOCK) {
                if (it.data != null && it.data.type == CARD_TAG) {
                   items.add(it.data)
                }
            }
            if (it.data?.childrens != null) {
                items.addAll(getItemsFromChildrens(it.data.childrens))
            }
        }

        val adapter = EventBlockAdapter(items)
        eventBlocksRecycler.adapter = adapter

        supportActionBar?.title = event.title
        eventDescription.text = event.description
        event.eventInfo?.data?.eventImage?.let {
            val url = it.substring(6, it.length - 2)
            Glide.with(this).load(BuildConfig.MAIN_URL + url).into(eventImg)
        }
        eventType.text = event.eventType?.name ?: getString(R.string.title_event_single)
        val color = event.getEventTypeColor()
        DrawableCompat.setTint(eventType.background, ContextCompat.getColor(this, color))
    }


    private fun getItemsFromChildrens(childs: List<Children>, items: MutableList<Data> = mutableListOf()): List<Data> {
        childs.forEach {
            if (it.blockId == REGISTRATION_BLOCK) {
                if (it.data != null && it.data.type == CARD_TAG) {
                    items.add(it.data)
                }
            }

            if (it.data?.childrens != null) {
                getItemsFromChildrens(it.data.childrens, items)
            }
        }
        return items
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    companion object {
        private val EVENT = "event"

        fun start(context: Context?, event: Event) {
            val intent = Intent(context, EventDetailsActivity::class.java)
            intent.putExtra(EVENT, event)
            context?.startActivity(intent)
        }
    }
}
