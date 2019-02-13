package com.foureyedstraighthair.ticks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.widget.FrameLayout

class BumpyView(
    context: Context, attrs: AttributeSet)
    : FrameLayout(context, attrs) {

    companion object {
        private const val DEFAULT_CONCAVE_RADIUS = 16 //dip
        private const val DEFAULT_CONCAVE_BACKGROUND_COLOR = Color.TRANSPARENT
    }

    private var isCornerRadiusChanged = true

    private val outline = Path()

    private val paint = Paint().apply {
        style = Paint.Style.FILL
        isAntiAlias = true
    }

    var cornerRadius = 0f
        set(value) {
            if (field != value) {
                field = value
                isCornerRadiusChanged = true
                invalidate()
            }
        }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.BumpyView, 0, 0)

        val backgroundColor = a.getColor(
            R.styleable.BumpyView_bumpyBackgroundColor,
            DEFAULT_CONCAVE_BACKGROUND_COLOR)

        cornerRadius = a.getDimensionPixelSize(
            R.styleable.BumpyView_bumpyCornerRadius,
            (context.resources.displayMetrics.density * DEFAULT_CONCAVE_RADIUS).toInt())
            .toFloat()

        a.recycle()

        super.setBackgroundColor(Color.TRANSPARENT)
        setBackgroundColor(backgroundColor)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        makeOutlinePath()
        canvas.drawPath(outline, paint)
    }

    override fun setBackgroundColor(color: Int) {
        paint.color = color
        invalidate()
    }

    private fun makeOutlinePath() {
        val notChanged = !isCornerRadiusChanged
        isCornerRadiusChanged = false
        when {

            notChanged -> return

            // convex shape
            0 < cornerRadius -> outline.apply {
                val r = cornerRadius
                reset()
                moveTo(0f, 2 * r)
                arcTo(0f, r, 2 * r, 3 * r, 180f, 90f, true)
                lineTo(width - r, r)
                arcTo(width - 2 * r, r, width.toFloat(), 3 * r, 270f, 90f, true)
                lineTo(width.toFloat(), height.toFloat())
                lineTo(0f, height.toFloat())
                lineTo(0f, 2 * r)
                close()
            }

            // concave shape
            else -> outline.apply {
                val r = -cornerRadius
                reset()
                moveTo(0f, 0f)
                arcTo(0f, -r, 2 * r, r, 180f, -90f, true)
                lineTo(width - r, r)
                arcTo(width - 2 * r, -r, width.toFloat(), r, 90f, -90f, true)
                lineTo(width.toFloat(), height.toFloat())
                lineTo(0f, height.toFloat())
                lineTo(0f, 0f)
                close()
            }
        }
    }
}