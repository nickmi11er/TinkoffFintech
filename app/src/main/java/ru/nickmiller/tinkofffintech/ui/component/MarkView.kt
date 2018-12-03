package ru.nickmiller.tinkofffintech.ui.component

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import ru.nickmiller.tinkofffintech.R
import ru.nickmiller.tinkofffintech.data.entity.course.GradeMark


class MarkView : View {
    private val defaultViewSize = resources.getDimensionPixelSize(R.dimen.mark_view_default_size)
    private val defaultTextSize = resources.getDimensionPixelSize(R.dimen.mark_view_text_size)

    private val paint = Paint().apply { color = Color.GRAY }
    private val textPaint = Paint().apply {
        color = ContextCompat.getColor(context, R.color.colorTextMain)
        textSize = defaultTextSize.toFloat()
        textAlign = Paint.Align.LEFT
    }
    private val rect by lazy { Rect(0, 0, width, height) }
    private val roundedRect by lazy { RectF(rect) }
    private var cornerRadius = 10F

    var mark: Double = 0.0
    var status: String = ""
    var taskType: String = "new"

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private fun init() {

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)

        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        val width = when (widthMode) {
            View.MeasureSpec.EXACTLY -> widthSize
            View.MeasureSpec.AT_MOST -> defaultViewSize
            View.MeasureSpec.UNSPECIFIED -> defaultViewSize
            else -> defaultViewSize
        }

        val height = when (heightMode) {
            View.MeasureSpec.EXACTLY -> heightSize
            View.MeasureSpec.AT_MOST -> defaultViewSize
            View.MeasureSpec.UNSPECIFIED -> defaultViewSize
            else -> defaultViewSize
        }

        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        val flags = paint.flags
        paint.color = colorOfStatus()
        canvas.drawRoundRect(roundedRect, cornerRadius, cornerRadius, paint)
        paint.flags = flags
        drawMark(canvas)
    }

    private fun drawMark(canvas: Canvas) {
        canvas.getClipBounds(rect)
        val cHeight = rect.height()
        val cWidth = rect.width()

        textPaint.getTextBounds(mark.toString(), 0, mark.toString().length, rect)
        val x = cWidth / 2F - rect.width() / 2F - rect.left
        val y = cHeight / 2F + rect.height() / 2F - rect.bottom
        canvas.drawText(mark.toString(), x, y, textPaint)
    }

    fun bind(gradeMark: GradeMark) {
        mark = gradeMark.mark ?: 0.0
        taskType = gradeMark.taskType ?: "new"
        status = gradeMark.status ?: ""
        invalidate()
    }

    private fun colorOfStatus(): Int {
        return when (status) {
            "new" -> ContextCompat.getColor(context, R.color.colorWeakGrey)
            "on_check" -> ContextCompat.getColor(context, R.color.colorWeakOrange)
            "need_work" -> ContextCompat.getColor(context, R.color.colorBlueSky)
            "accepted" -> ContextCompat.getColor(context, R.color.colorGrass)
            "failed" -> ContextCompat.getColor(context, R.color.colorSkin)
            else -> ContextCompat.getColor(context, R.color.colorWeakGrey)
        }
    }

    private fun isBordered(): Boolean =
        taskType == "full" && (status == "on_check" || status == "need_work")
}