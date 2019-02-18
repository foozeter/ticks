package com.foureyedstraighthair.ticks.jam.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.inline.InlineFloatPropertyAnim

class FloatPropertyAnim(
    jam: Jam,
    definition: InlineFloatPropertyAnim)
    : PropertyAnim(jam, definition) {

    val startValue = definition.startValue
    val endValue = definition.endValue

    override fun onCreateAnimator(target: View): Animator =
        if (property != null) ObjectAnimator.ofFloat(target, property, startValue, endValue)
        else throw IllegalStateException("Specify the property name.")
}