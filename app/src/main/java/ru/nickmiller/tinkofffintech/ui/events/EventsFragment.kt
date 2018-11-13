package ru.nickmiller.tinkofffintech.ui.events


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.ui.MainActivity
import ru.nickmiller.tinkofffintech.ui.base.BaseFragment
import ru.nickmiller.tinkofffintech.utils.find
import kotlin.math.min


class EventsFragment : BaseFragment() {
    val viewModel by viewModel<EventsViewModel>()
    override var title = "Мероприятия"
    override val contentView = R.layout.fragment_events
    lateinit var actualEvents: EventsContainer
    lateinit var archiveEvents: EventsContainer


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actualEvents = view.find(R.id.events_actual)
        archiveEvents = view.find(R.id.events_archive)
        actualEvents.setLayoutManager(LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false))
        setUpObservable()
    }

    private fun setUpObservable() {
        viewModel.eventsObservable.observe(this, Observer { res ->
            when (res?.status) {
                Resource.Status.LOADING -> (activity as MainActivity).startProgress()
                Resource.Status.ERROR -> (activity as MainActivity).showError(res.error?.message)
                Resource.Status.SUCCESS -> {
                    (activity as MainActivity).stopProgress()
                    res.data?.let { events ->
                        val actual = events.filter { it.isActual }
                        val aAdapter = EventsAdapter(context, actual.subList(0, min(actual.size, 5)), true)
                        actualEvents.setAdapter(aAdapter)
                        actualEvents.setCount(actual.size)
                        actualEvents.addOnAllClickListener {
                            EventsListActivity.start(context, EventsListActivity.EventsType.ACTUAL, actual)
                        }

                        val archive = events.filter { !it.isActual }
                        val arcAdapter = EventsAdapter(context, archive.subList(0, min(archive.size, 5)))
                        archiveEvents.setAdapter(arcAdapter)
                        archiveEvents.setCount(archive.size)
                        archiveEvents.addOnAllClickListener {
                            EventsListActivity.start(context, EventsListActivity.EventsType.ARCHIVE, archive)
                        }
                    }
                }
            }
        })
    }
}
