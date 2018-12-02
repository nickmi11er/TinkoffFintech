package ru.nickmiller.tinkofffintech.ui.component

import android.content.Context
import android.graphics.*
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import ru.nickmiller.tinkofffintech.R


class CircleProgressBar : View {
    private lateinit var paint: Paint
    private var pointAngle: Double = 0.toDouble()
    private var point: Int = 0
    private lateinit var rect: RectF

    var progressWidth: Float = 0.toFloat()
    var bgColor: Int = 0
    var progressStartAngel: Int = 0
    var progressStart: Int = 0
    var pointSize = 0
    var progressColor: Int = 0
    var progressEnd: Int = 0
        set(value) {
            field = value
            pointAngle = Math.abs(progressStartAngel).toDouble() / (progressEnd - progressStart)
            invalidate()
        }

    var progress: Int = 0
        set(value) {
            field = value
            point = (progressStartAngel + (progress - progressStart) * pointAngle).toInt()
            invalidate()
        }

    constructor(context: Context?) : super(context) {
        init()
    }


    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val osa = context.obtainStyledAttributes(attrs, R.styleable.CircleProgressBar, 0, 0)

        progressWidth = osa.getDimension(R.styleable.CircleProgressBar_progressWidth, 10f)
        bgColor = osa.getColor(
            R.styleable.CircleProgressBar_progressBgColor,
            ContextCompat.getColor(context, android.R.color.darker_gray)
        )
        progressStartAngel = osa.getInt(R.styleable.CircleProgressBar_progressStartAngel, 0)

        progressStart = osa.getInt(R.styleable.CircleProgressBar_progressStart, 0)
        progressEnd = osa.getInt(R.styleable.CircleProgressBar_progressEnd, 100)

        pointSize = osa.getInt(R.styleable.CircleProgressBar_progressPointSize, 0)
        progressColor =
                osa.getColor(
                    R.styleable.CircleProgressBar_progressColor,
                    ContextCompat.getColor(context, android.R.color.white)
                )

        pointAngle = Math.abs(360).toDouble() / (progressEnd - progressStart)
        osa.recycle()
        init()
    }


    private fun init() {
        paint = Paint()
        paint.color = bgColor
        paint.strokeWidth = progressWidth
        paint.isAntiAlias = true
        paint.strokeCap = Paint.Cap.ROUND
        paint.style = Paint.Style.STROKE
        rect = RectF()

        progress = progressStart
        point = progressStartAngel
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val paddingLeft = paddingLeft.toFloat()
        val paddingRight = paddingRight.toFloat()
        val paddingTop = paddingTop.toFloat()
        val paddingBottom = paddingBottom.toFloat()
        val width = width - (paddingLeft + paddingRight)
        val height = height - (paddingTop + paddingBottom)
        val radius = if (width > height) width / 2 else height / 2

        val rectLeft = width / 2 - radius + paddingLeft
        val rectTop = height / 2 - radius + paddingTop
        val rectRight = width / 2 - radius + paddingLeft + width
        val rectBottom = height / 2 - radius + paddingTop + height

        rect.set(rectLeft, rectTop, rectRight, rectBottom)

        paint.color = bgColor
        paint.shader = null
        canvas.drawArc(rect, progressStartAngel.toFloat(), 360F, false, paint)
        paint.color = progressColor

        if (progress == progressStart)
            canvas.drawArc(rect, progressStartAngel.toFloat(), 1F, false, paint)
        else
            canvas.drawArc(rect, progressStartAngel.toFloat(), (point - progressStartAngel).toFloat(), false, paint)
    }
}