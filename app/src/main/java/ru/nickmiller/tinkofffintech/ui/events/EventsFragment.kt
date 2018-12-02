package ru.nickmiller.tinkofffintech.ui.events


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.fragment_events.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.ui.MainActivity
import ru.nickmiller.tinkofffintech.ui.base.BaseFragment
import ru.nickmiller.tinkofffintech.ui.events.details.EventDetailsDialog
import kotlin.math.min


class EventsFragment : BaseFragment() {
    val viewModel by viewModel<EventsViewModel>()
    override var title = getString(R.string.title_events)
    override val contentView = R.layout.fragment_events

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events_actual.setLayoutManager(
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        )

        observeEvents()
        events_refresh.setOnRefreshListener { viewModel.getEvents(true) }
    }

    private fun observeEvents() {
        viewModel.dataObservable.observe(this, Observer { events ->
            container.visibility = View.VISIBLE
            events?.let {
                val actual = events.filter { it.isActual }
                val aAdapter = EventsAdapter(
                    context,
                    actual.subList(0, min(actual.size, 5)),
                    true
                )
                events_actual.setAdapter(aAdapter)
                events_actual.setCount(actual.size)
                events_actual.addOnAllClickListener {
                    EventsListActivity.start(
                        context,
                        EventsListActivity.EventsType.ACTUAL,
                        actual
                    )
                }

                val archive = events.filter { !it.isActual }
                val arcAdapter = EventsAdapter(context, archive.subList(0, min(archive.size, 5)))
                arcAdapter.addOnEventClickListener {
                    EventDetailsDialog.showEventDetails(fragmentManager, it)
                }
                events_archive.setAdapter(arcAdapter)
                events_archive.setCount(archive.size)
                events_archive.addOnAllClickListener {
                    EventsListActivity.start(context, EventsListActivity.EventsType.ARCHIVE, archive)
                }
            }
        })

        viewModel.errorsObservable.observe(this, Observer {
            events_refresh.isRefreshing = false
            (activity as MainActivity).showError(it?.message)
        })

        viewModel.loadingObservable.observe(this, Observer {
            if (it == true) {
                (activity as MainActivity).startProgress()
            } else {
                events_refresh.isRefreshing = false
                (activity as MainActivity).stopProgress()
            }
        })
    }
}
