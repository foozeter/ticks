package com.foureyedstraighthair.ticks.jam.constant

import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.view.animation.*
import com.foureyedstraighthair.ticks.Attrs

enum class EInterpolator(
    override val id: Int)
    : Attrs.IdentifiableEnum {

    LINEAR(0),
    LINEAR_OUT_SLOW_IN(1),
    ACCELERATE(2),
    DECELERATE(3),
    ACCELERATE_DECELERATE(4),
    ANTICIPATE(5),
    ANTICIPATE_OVERSHOOT(6);

    fun generate() = when (this) {
        LINEAR -> LinearInterpolator()
        LINEAR_OUT_SLOW_IN -> LinearOutSlowInInterpolator()
        ACCELERATE -> AccelerateInterpolator()
        DECELERATE -> DecelerateInterpolator()
        ACCELERATE_DECELERATE -> AccelerateDecelerateInterpolator()
        ANTICIPATE -> AnticipateInterpolator()
        ANTICIPATE_OVERSHOOT -> AnticipateOvershootInterpolator()
    }
}