package ru.nickmiller.tinkofffintech.ui.events.details

import android.text.Html
import android.view.View
import kotlinx.android.synthetic.main.view_event_block_item.*
import ru.nickmiller.tinkofffintech.data.entity.event.Data
import ru.nickmiller.tinkofffintech.ui.base.BaseViewHolder


class EventBlockViewHolder(itemView: View) : BaseViewHolder<Data>(itemView) {

    override fun bind(item: Data) {
        with(item) {
            eventBlockTitle.text = title
            eventBlockSubtitle.text = subtitle
            eventBlockAbout.text = Html.fromHtml(about)
            eventBlockDescription.text = Html.fromHtml(text)
            eventBlockBtn.text = buttonText
        }
    }
}