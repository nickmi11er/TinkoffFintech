package ru.nickmiller.tinkofffintech.ui.events.details

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import kotlinx.android.synthetic.main.dialog_event_details.view.*
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.event.Event
import ru.nickmiller.tinkofffintech.data.entity.event.getEventTypeColor


class EventDetailsDialog : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(context!!)
        val inflater = activity!!.layoutInflater

        val rootView = inflater.inflate(R.layout.dialog_event_details, null)
        builder.setView(rootView)
            .setPositiveButton(getString(R.string.msg_ok)) { dialog, id ->
                dialog.cancel()
            }

        val event = arguments?.getParcelable(EVENT) as Event
        val color = event.getEventTypeColor()

        rootView.headerContainer.setBackgroundColor(ContextCompat.getColor(context!!, color))
        rootView.eventTitle.text = event.title
        rootView.eventType.text = event.eventType?.name ?: getString(R.string.title_event_single)
        rootView.eventDescription.text = event.description
        rootView.eventPlace.text = event.place
        return builder.create()
    }


    companion object {
        private val DIALOG_TAG = "eventDetailsDialog"
        private val EVENT = "event"

        fun showEventDetails(fragmentManager: FragmentManager?, event: Event) {
            val dialog = EventDetailsDialog()
            dialog.arguments = Bundle().apply { putParcelable(EVENT, event) }
            dialog.show(
                fragmentManager,
                DIALOG_TAG
            )
        }
    }

}