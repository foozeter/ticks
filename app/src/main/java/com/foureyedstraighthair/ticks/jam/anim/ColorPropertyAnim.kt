package com.foureyedstraighthair.ticks.jam.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.inline.InlineColorPropertyAnim

class ColorPropertyAnim(
    jam: Jam,
    definition: InlineColorPropertyAnim)
    : PropertyAnim(jam, definition) {

    private val startColor = definition.startColor
    private val endColor = definition.endColor

    override fun onCreateAnimator(target: View): Animator =
            if (property != null) ObjectAnimator.ofArgb(target, property, startColor, endColor)
            else throw IllegalStateException("Specify the property name.")
}
