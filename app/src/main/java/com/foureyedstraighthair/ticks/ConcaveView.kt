package com.foureyedstraighthair.ticks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout

class ConcaveView(
    context: Context, attrs: AttributeSet)
    : FrameLayout(context, attrs) {

    companion object {
        private const val DEFAULT_CONCAVE_RADIUS = 16 //dip
        private const val DEFAULT_CONCAVE_BACKGROUND_COLOR = Color.TRANSPARENT
    }

    private var isConcaveRadiusChanged = true

    private val outline = Path()

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
        color = concaveBackgroundColor
    }

    var concaveRadius = 0f
        set(value) {
            field = value
            isConcaveRadiusChanged = true
            invalidate()
        }

    var concaveBackgroundColor = 0
        set(value) {
            field = value
            paint.color = value
            invalidate()
        }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.ConcaveView, 0, 0)

        concaveBackgroundColor = a.getColor(
            R.styleable.ConcaveView_concaveBackgroundColor,
            DEFAULT_CONCAVE_BACKGROUND_COLOR)

        concaveRadius = a.getDimensionPixelSize(
            R.styleable.ConcaveView_concaveRadius,
            (context.resources.displayMetrics.density * DEFAULT_CONCAVE_RADIUS).toInt())
            .toFloat()

        a.recycle()

        setBackgroundColor(Color.TRANSPARENT)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        makeOutlinePath()
        canvas.drawPath(outline, paint)
    }

    private fun makeOutlinePath() {
        if (isConcaveRadiusChanged) {
            outline.apply {
                reset()
                moveTo(0f, 0f)
                arcTo(0f, -concaveRadius, 2 * concaveRadius, concaveRadius, 180f, -90f, true)
                lineTo(width - concaveRadius, concaveRadius)
                arcTo(width - 2 * concaveRadius, -concaveRadius, width.toFloat(), concaveRadius, 90f, -90f, true)
                lineTo(width.toFloat(), height.toFloat())
                lineTo(0f, height.toFloat())
                lineTo(0f, 0f)
                close()
            }
        }
    }
}