package ru.nickmiller.tinkofffintech.ui.courses


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_courses.*
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.Resource
import ru.nickmiller.tinkofffintech.ui.MainActivity
import ru.nickmiller.tinkofffintech.ui.base.BaseFragment


class CoursesFragment : BaseFragment() {
    val viewModel by viewModel<CoursesViewModel>()
    override var title = "Мои курсы"
    override val contentView = R.layout.fragment_courses


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        refresher.setOnRefreshListener { viewModel.getCourses(true) }
    }

    private fun initObservers() {
        viewModel.dataObservable.observe(this, Observer { courses ->
            courses?.let {
                val adapter = CoursesAdapter(courses)
                coursesRecycler.adapter = adapter
            }
        })

        viewModel.errorsObservable.observe(this, Observer {
            Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
        })

        viewModel.loadingObservable.observe(this, Observer { progress ->
            if (progress == true) {
                (activity as MainActivity).startProgress()
            } else {
                refresher.isRefreshing = false
                (activity as MainActivity).stopProgress()
            }
        })
    }
}
