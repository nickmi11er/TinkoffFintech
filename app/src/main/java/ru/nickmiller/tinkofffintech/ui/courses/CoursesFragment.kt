package ru.nickmiller.tinkofffintech.ui.courses


import android.arch.lifecycle.Observer
import android.os.Bundle
import android.view.View
import android.widget.Toast
import org.koin.android.viewmodel.ext.android.viewModel
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.ui.base.BaseFragment
import ru.nickmiller.tinkofffintech.ui.component.CircleProgressBar
import ru.nickmiller.tinkofffintech.utils.find


class CoursesFragment : BaseFragment() {
    val viewModel by viewModel<CoursesViewModel>()
    override var title = "Мои курсы"
    override val contentView = R.layout.fragment_courses

    lateinit var statProgress: CircleProgressBar

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        statProgress = view.find(R.id.stat_progress)
        statProgress.progress = 50


        viewModel.coursesObservable.observe(this, Observer {
            Toast.makeText(context, "HAHA", Toast.LENGTH_SHORT).show()
        })
    }
}
