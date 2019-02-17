package com.foureyedstraighthair.ticks.jam

import android.content.Context
import android.support.annotation.IdRes
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.Attrs
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.constant.Default
import com.foureyedstraighthair.ticks.jam.constant.EInterpolator
import com.foureyedstraighthair.ticks.jam.constant.TriggerEvent

open class InlineAnim(
    context: Context,
    attributeSet: AttributeSet)
    : UndrawnView(context, attributeSet) {

    val duration: Long

    val startDelay: Long

    @IdRes
    val target: Int

    @IdRes
    val trigger: Int

    val targetFlagBefore: Int
    
    val targetFlagAfter: Int

    val triggerEvent: TriggerEvent

    val interpolator: EInterpolator

    init {
        val attrs = Attrs(
            context,
            attributeSet,
            R.styleable.InlineAnim)

        duration = attrs.fetchInt(
            R.styleable.InlineAnim_jam_duration,
            Default.ANIMATION_DURATION).toLong()

        startDelay = attrs.fetchInt(
            R.styleable.InlineAnim_jam_startDelay,
            Default.ANIMATION_START_DELAY).toLong()

        target = attrs.fetchResourceID(
            R.styleable.InlineAnim_jam_target)

        trigger = attrs.fetchResourceID(
            R.styleable.InlineAnim_jam_trigger)

        triggerEvent = attrs.fetchEnum(
            R.styleable.InlineAnim_jam_triggerEvent,
            TriggerEvent.ON_CLICK,
            TriggerEvent::class.java)

        targetFlagBefore = attrs.fetchInt(
            R.styleable.InlineAnim_jam_targetFlagBefore,
            Default.TARGET_FLAG)

        targetFlagAfter = attrs.fetchInt(
            R.styleable.InlineAnim_jam_targetFlagAfter,
            Default.TARGET_FLAG)

        interpolator = attrs.fetchEnum(
            R.styleable.InlineAnim_jam_interpolator,
            EInterpolator.LINEAR,
            EInterpolator::class.java)

        attrs.recycle()
    }
}