package com.foureyedstraighthair.ticks.jam

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View

class ColorAnim(
    jam: Jam,
    definition: InlineColorAnim)
    : Anim(jam, definition) {

    private val startColor = definition.startColor
    private val endColor = definition.endColor
    private val property = definition.property

    override fun onCreateAnimator(target: View): Animator
            = ObjectAnimator.ofArgb(
            target, property.toString(), startColor, endColor)
}
