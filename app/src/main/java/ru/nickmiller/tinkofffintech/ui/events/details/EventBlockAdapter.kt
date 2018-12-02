package ru.nickmiller.tinkofffintech.ui.events.details

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.event.Data


class EventBlockAdapter(val blocks: List<Data>) : RecyclerView.Adapter<EventBlockViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventBlockViewHolder =
        EventBlockViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_event_block_item, parent, false)
        )


    override fun onBindViewHolder(holder: EventBlockViewHolder, position: Int) {
        holder.bind(blocks[position])
    }

    override fun getItemCount() = blocks.size

}