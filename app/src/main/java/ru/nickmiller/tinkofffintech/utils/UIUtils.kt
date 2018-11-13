package ru.nickmiller.tinkofffintech.utils

import android.animation.Animator
import android.app.Activity
import android.content.Context
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AccelerateDecelerateInterpolator

inline fun <reified V : View> Activity.find(id: Int): V = findViewById(id)

inline fun <reified V : View> Fragment.find(id: Int): V? = view?.findViewById(id)

inline fun <reified V : View> RecyclerView.ViewHolder.find(id: Int): V = itemView.findViewById(id)

inline fun <reified V : View> View.find(id: Int): V = findViewById(id)


@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
fun revealView(view: View, duration: Long, startRadius: Float): Animator {
    val cx = view.measuredWidth
    val cy = view.measuredHeight

    val finalRadius = Math.max(view.width, view.height)

    val anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, startRadius, finalRadius.toFloat())
    anim.duration = duration
    anim.interpolator = AccelerateDecelerateInterpolator()

    return anim
}


fun dpToPx(context: Context, valueInDp: Float): Float {
    val metrics = context.resources.displayMetrics
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics)
}
 
 
