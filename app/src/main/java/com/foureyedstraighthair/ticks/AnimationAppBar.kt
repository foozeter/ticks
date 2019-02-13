package com.foureyedstraighthair.ticks

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.widget.FrameLayout
import kotlin.math.abs

class AnimationAppBar(
    context: Context,
    attrs: AttributeSet)
    : FrameLayout(context, attrs) {

    private val paint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 10f
    }

    private val sticks = listOf(
        Stick(paint), Stick(paint), Stick(paint),
        Stick(paint), Stick(paint), Stick(paint))

    private val density = context.resources.displayMetrics.density
    private val _1dp = density * 1
    private val _2dp = density * 2
    private val _4dp = density * 4
    private val _12dp = density * 12
    private val _16dp = density * 16
    private val _24dp = density * 24
    private val _28dp = density * 28
    private val _32dp = density * 32

    var progress = 0f

    init {
        setWillNotDraw(false)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val cx = measuredWidth / 2f
        val w = measuredWidth.toFloat()
        // hamburger icon
        sticks[0].set(_28dp, _24dp, cx, _12dp, _16dp, _24dp)
        sticks[1].set(_28dp, _28dp, cx, _12dp, _16dp, _24dp)
        sticks[2].set(_28dp, _32dp, cx, _12dp, _16dp, _24dp)
        // dots icon
        sticks[3].set(w - _24dp, _24dp, cx, _12dp, _1dp, _24dp)
        sticks[4].set(w - _24dp, _28dp, cx, _12dp, _1dp, _24dp)
        sticks[5].set(w - _24dp, _32dp, cx, _12dp, _1dp, _24dp)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // hamburger icon
        paint.strokeWidth = _2dp
        for (i in 0..2) sticks[i].drawOn(canvas, progress)
        // dots icon
        paint.strokeWidth = _4dp
        for (i in 3..5) sticks[i].drawOn(canvas, progress)
    }

    private class Stick(
        private val paint: Paint) {
        private val start = PointF()
        private val end = PointF()
        private val fraction = PointF()
        private val d = PointF()
        private var ld2 = 0f
        private var dl = 0f
        private var cannotDraw = true

        fun set(startX: Float, startY: Float, endX: Float, endY: Float, startLength: Float, endLength: Float) {
            ld2 = startLength / 2
            dl = endLength - startLength
            start.set(startX, startY)
            end.set(endX, endY)
            d.x = (endX - startX)
            d.y = (endY - startY)
            val distance = abs(d.x) + abs(d.y)
            fraction.x = abs(d.x / distance)
            fraction.y = abs(d.y / distance)
            cannotDraw = fraction.x * fraction.y == 0f
        }

        fun drawOn(canvas: Canvas, progress: Float) {
            if (cannotDraw) return
            if (progress <= fraction.y) {
                val y = start.y + d.y * (progress / fraction.y)
                canvas.drawLine(start.x - ld2, y, start.x + ld2, y, paint)
            } else {
                val f = (progress - fraction.y) / fraction.x
                val x = start.x + d.x * f
                val l = ld2 + dl * f / 2
                canvas.drawLine(x - l, end.y, x + l, end.y, paint)
            }
        }
    }
}