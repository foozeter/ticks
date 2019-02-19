package com.foureyedstraighthair.ticks.jam.anim

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.inline.anim.InlineColorPropertyAnim
import com.foureyedstraighthair.ticks.jam.inline.property.InlineColorProperty
import com.foureyedstraighthair.ticks.jam.property.ColorProperty

class ColorPropertyAnim(jam: Jam, definition: InlineColorPropertyAnim)
    : PropertyAnim<Int, ColorProperty, InlineColorProperty, InlineColorPropertyAnim>(
    jam, definition, ColorProperty::class.java, InlineColorProperty::class.java) {

    private val propertyHolders = properties.map {
        PropertyValuesHolder
            .ofInt(it.name, it.startValue, it.endValue)
            .apply { setEvaluator(ArgbEvaluator()) }
    }.toTypedArray()

    override fun onCreateAnimator(target: View): Animator =
        ObjectAnimator.ofPropertyValuesHolder(target, *propertyHolders).apply {
            setEvaluator(ArgbEvaluator())
        }
}
