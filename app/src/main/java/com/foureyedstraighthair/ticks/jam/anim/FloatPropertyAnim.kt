package com.foureyedstraighthair.ticks.jam.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.helper.FloatPropertyName
import com.foureyedstraighthair.ticks.jam.inline.InlineFloatProperty
import com.foureyedstraighthair.ticks.jam.inline.InlineFloatPropertyAnim
import com.foureyedstraighthair.ticks.jam.property.FloatProperty

class FloatPropertyAnim(jam: Jam, definition: InlineFloatPropertyAnim)
    : PropertyAnim<Float, FloatProperty, InlineFloatProperty, InlineFloatPropertyAnim>(
    jam, definition, FloatProperty::class.java, InlineFloatProperty::class.java) {

    override fun onCreateAnimator(target: View): Animator =
        ObjectAnimator.ofPropertyValuesHolder(target,
            *properties
                .map {
                    val startValue =
                        if (it.startWithCurrentValue) FloatPropertyName.getValueOf(target, it.name)
                        else it.startValue

                    return@map PropertyValuesHolder.ofFloat(it.name, startValue, it.endValue)
                }
                .toTypedArray())
}