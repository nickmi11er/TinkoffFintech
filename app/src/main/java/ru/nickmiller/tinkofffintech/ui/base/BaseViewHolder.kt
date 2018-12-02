package ru.nickmiller.tinkofffintech.ui.base

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<T>(itemView: View) :
    RecyclerView.ViewHolder(itemView), LayoutContainer {

    override val containerView = itemView
    abstract fun bind(item: T)
}
 
