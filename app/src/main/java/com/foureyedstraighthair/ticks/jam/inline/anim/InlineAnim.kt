package com.foureyedstraighthair.ticks.jam.inline.anim

import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.Default

open class InlineAnim(
    context: Context,
    attributeSet: AttributeSet)
    : ViewGroup(context, attributeSet) {

    val duration: Long

    val startDelay: Long
    
    @IdRes
    val target: Int

    @IdRes
    val trigger: Int

    val targetStateBefore: Int
    
    val targetStateAfter: Int

    val triggerEventFlags: Int

    var allowInterruption: Boolean

    val interpolator: Interpolator

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineAnim, 0, 0)

        duration = attrs.getInt(
            R.styleable.InlineAnim_jam_duration,
            Default.ANIMATION_DURATION).toLong()

        startDelay = attrs.getInt(
            R.styleable.InlineAnim_jam_startDelay,
            Default.ANIMATION_START_DELAY).toLong()
        
        target = attrs.getResourceId(
            R.styleable.InlineAnim_jam_target, 0)

        trigger = attrs.getResourceId(
            R.styleable.InlineAnim_jam_trigger, 0)

        triggerEventFlags = attrs.getInt(
            R.styleable.InlineAnim_jam_triggerEvents,
            Default.TRIGGER_EVENT_FLAGS)

        targetStateBefore = attrs.getInt(
            R.styleable.InlineAnim_jam_targetStateBefore,
            Default.TARGET_FLAG)

        targetStateAfter = attrs.getInt(
            R.styleable.InlineAnim_jam_targetStateAfter,
            Default.TARGET_FLAG)

        allowInterruption = attrs.getBoolean(
            R.styleable.InlineAnim_jam_allowInterruption,
            Default.ALLOW_INTERRUPTION)

        interpolator = when (attrs.getInt(R.styleable.InlineAnim_jam_interpolator, 0)) {
            context.resources.getInteger(R.integer.enum_interpolator_linear) -> LinearInterpolator()
            context.resources.getInteger(R.integer.enum_interpolator_linearOutSlowIn) -> LinearOutSlowInInterpolator()
            context.resources.getInteger(R.integer.enum_interpolator_accelerate) -> AccelerateInterpolator()
            context.resources.getInteger(R.integer.enum_interpolator_decelerate) -> DecelerateInterpolator()
            context.resources.getInteger(R.integer.enum_interpolator_accelerateDecelerate) -> AccelerateDecelerateInterpolator()
            context.resources.getInteger(R.integer.enum_interpolator_anticipate) -> AnticipateInterpolator()
            context.resources.getInteger(R.integer.enum_interpolator_anticipateOvershoot) -> AnticipateOvershootInterpolator()
            else -> LinearInterpolator()
        }

        attrs.recycle()

        visibility = View.GONE
        setWillNotDraw(false)
    }

    final override fun setWillNotDraw(willNotDraw: Boolean)
            = super.setWillNotDraw(willNotDraw)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // Do nothing.
    }
}