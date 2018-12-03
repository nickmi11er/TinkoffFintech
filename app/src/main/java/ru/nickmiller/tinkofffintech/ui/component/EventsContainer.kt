package ru.nickmiller.tinkofffintech.ui.component

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.TextView
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.ui.events.EventsAdapter
import ru.nickmiller.tinkofffintech.utils.find


class EventsContainer : CardView {
    lateinit var eventsType: TextView
    private lateinit var eventsCount: TextView
    private lateinit var eventsContainer: RecyclerView
    private lateinit var adapter: EventsAdapter
    private var onAllClickListener: (() -> Unit)? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private fun init(context: Context?, attrs: AttributeSet?) {
        inflate(context, R.layout.view_events_container, this)
        eventsType = find(R.id.events_type)
        eventsCount = find(R.id.events_count)
        eventsCount.setOnClickListener { onAllClickListener?.invoke() }
        eventsContainer = find(R.id.events_container)
        eventsContainer.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        context?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.EventsContainer,
            0, 0
        )?.apply {
            try {
                val type = getString(R.styleable.EventsContainer_events_type)
                val count = getInteger(R.styleable.EventsContainer_events_count, 0)

                eventsType.text = type
                eventsCount.text = "Все $count"
            } finally {
                recycle()
            }
        }
    }

    fun setCount(c: Int) {
        eventsCount.text = "Все $c"
    }

    fun setAdapter(adapter: EventsAdapter) {
        this.adapter = adapter
        eventsContainer.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun setLayoutManager(manager: RecyclerView.LayoutManager) {
        eventsContainer.layoutManager = manager
    }

    fun addOnAllClickListener(onAllClickListener: () -> Unit) {
        this.onAllClickListener = onAllClickListener
    }
}