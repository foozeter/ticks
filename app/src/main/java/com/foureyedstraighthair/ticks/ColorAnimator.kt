package com.foureyedstraighthair.ticks

import android.animation.ValueAnimator

abstract class ColorAnimator<T> {

    private val targets = mutableListOf<T>()
    private var animator: ValueAnimator? = null
    private var startColor = 0
    private var endColor = 0
    private var duration = 0L

    fun duration(duration: Long): ColorAnimator<T> {
        this.duration = duration
        return this
    }

    fun targets(vararg targets: T): ColorAnimator<T> {
        this.targets.addAll(targets)
        return this
    }

    fun from(startColor: Int): ColorAnimator<T> {
        this.startColor = startColor
        return this
    }

    fun to(endColor: Int): ColorAnimator<T> {
        this.endColor = endColor
        return this
    }

    fun cancel() {
        animator?.cancel()
    }

    fun start() {
        cancel()
        val anim = ValueAnimator.ofArgb(startColor, endColor)
        animator = anim
        anim.duration = duration
        anim.addUpdateListener {
            val color = it.animatedValue as Int
            targets.forEach { view -> onUpdate(view, color) }
        }
        anim.start()
    }

    protected abstract fun onUpdate(target: T, animatedColor: Int)
}