package ru.nickmiller.tinkofffintech.ui.component

import android.content.Context
import android.content.res.ColorStateList
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.pnikosis.materialishprogress.ProgressWheel
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.utils.find


class ProgressButton : FrameLayout {

    private lateinit var viewText: TextView
    private lateinit var viewProgress: ProgressWheel

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }


    private fun init(context: Context?, attrs: AttributeSet?) {
        inflate(context, R.layout.view_progress_button, this)
        viewText = find(R.id.text)
        viewProgress = find(R.id.progress_bar)

        context?.theme?.obtainStyledAttributes(
            attrs,
            R.styleable.ProgressButton,
            0, 0
        )?.apply {
            try {
                val text = getString(R.styleable.ProgressButton_text)
                val color = getColor(
                    R.styleable.ProgressButton_bg_tint,
                    ResourcesCompat.getColor(resources, R.color.colorPrimary, null)
                )
                val s = getDimension(R.styleable.ProgressButton_text_size, 14F)

                viewText.text = text
                val bg = ContextCompat.getDrawable(context, R.drawable.bg_button_light)

                ViewCompat.setBackgroundTintList(
                    this@ProgressButton,
                    ColorStateList(arrayOf(IntArray(0)), intArrayOf(color))
                )
                background = bg
            } finally {
                recycle()
            }
        }
    }

    fun showProgress() {
        viewText.visibility = View.INVISIBLE
        if (viewProgress.visibility != View.VISIBLE) {
            viewProgress.visibility = View.VISIBLE
        }
        viewProgress.spin()
    }

    fun hideProgress() {
        viewText.visibility = View.VISIBLE
        viewProgress.stopSpinning()
    }
}