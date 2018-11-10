package ru.nickmiller.tinkofffintech.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.utils.find


abstract class BaseFragment : Fragment() {

    var toolbar: Toolbar? = null
    abstract var title: String
    abstract val contentView: Int

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView =  inflater.inflate(contentView, container, false)
        toolbar = rootView.find(R.id.toolbar)
        toolbar?.let { it.title = title }

        return rootView
    }
}