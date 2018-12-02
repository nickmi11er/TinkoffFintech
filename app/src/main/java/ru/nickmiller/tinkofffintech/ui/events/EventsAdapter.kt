package ru.nickmiller.tinkofffintech.ui.events

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.ui.events.details.EventDetailsActivity
import ru.nickmiller.tinkofffintech.utils.find


class EventsAdapter(
    val context: Context?,
    var events: List<Event>,
    val isActual: Boolean = false,
    val large: Boolean = false
) :
    RecyclerView.Adapter<EventsViewHolder>(), Filterable {

    private val ITEM_DEFAULT_VIEWTYPE = 0
    private val ITEM_LAST_VIEWTYPE = 1

    private var eventsFiltered: List<Event> = events
    private var eventClickListener: ((event: Event) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventsViewHolder {
        val layout =
            if (isActual) {
                if (large) R.layout.view_events_actual_item_large
                else R.layout.view_events_actual_item
            } else R.layout.view_events_archive_item

        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        if (viewType == ITEM_LAST_VIEWTYPE && !isActual) {
            view.find<View>(R.id.event_item_divider).visibility = View.GONE
        }
        return if (isActual) ActualEventsViewHolder(view, large)
        else ArchiveEventsViewHolder(view, large)
    }


    override fun getItemViewType(position: Int) =
        if (position == eventsFiltered.count() - 1) ITEM_LAST_VIEWTYPE
        else ITEM_DEFAULT_VIEWTYPE


    override fun getItemCount(): Int = eventsFiltered.size


    override fun onBindViewHolder(holder: EventsViewHolder, position: Int) {
        val event = eventsFiltered[position]
        holder.bind(event)
        holder.itemView.setOnClickListener {
            if (!event.description.isNullOrEmpty()) {
                if (eventClickListener != null) eventClickListener!!.invoke(event)
                else EventDetailsActivity.start(context, event)
            } else {
                Toast.makeText(
                    context,
                    context?.getString(R.string.error_null_event_description),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }


    fun addOnEventClickListener(listener: (event: Event) -> Unit) {
        eventClickListener = listener
    }


    override fun getFilter(): Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val charString = constraint.toString()
            eventsFiltered = if (charString.isEmpty()) {
                events
            } else {
                events.filter {
                    it.title.contains(charString, true) ?: false
                }
            }
            return FilterResults().apply { values = eventsFiltered }
        }

        override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
            eventsFiltered = results?.values as MutableList<Event>
            notifyDataSetChanged()
        }
    }
}