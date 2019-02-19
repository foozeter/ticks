package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.support.annotation.IdRes
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.util.AttributeSet
import android.view.animation.*
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.Default

open class InlineAnim(
    context: Context,
    attributeSet: AttributeSet)
    : InlineBase(context, attributeSet) {

    val duration: Long

    val startDelay: Long
    
    @IdRes
    val target: Int

    @IdRes
    val trigger: Int

    val targetFlagBefore: Int
    
    val targetFlagAfter: Int

    val triggerEventFlags: Int

    val interpolator: Interpolator

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineBase, 0, 0)

        duration = attrs.getInt(
            R.styleable.InlineBase_jam_duration,
            Default.ANIMATION_DURATION).toLong()

        startDelay = attrs.getInt(
            R.styleable.InlineBase_jam_startDelay,
            Default.ANIMATION_START_DELAY).toLong()
        
        target = attrs.getResourceId(
            R.styleable.InlineBase_jam_target, 0)

        trigger = attrs.getResourceId(
            R.styleable.InlineBase_jam_trigger, 0)

        triggerEventFlags = attrs.getInt(
            R.styleable.InlineBase_jam_triggerEvents,
            Default.TRIGGER_EVENT_FLAGS)

        targetFlagBefore = attrs.getInt(
            R.styleable.InlineBase_jam_targetFlagBefore,
            Default.TARGET_FLAG)

        targetFlagAfter = attrs.getInt(
            R.styleable.InlineBase_jam_targetFlagAfter,
            Default.TARGET_FLAG)

        interpolator = when (attrs.getInt(R.styleable.InlineBase_jam_interpolator, 0)) {
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
    }
}