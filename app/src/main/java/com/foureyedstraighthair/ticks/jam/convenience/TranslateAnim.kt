package com.foureyedstraighthair.ticks.jam.convenience

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.ViewGroup
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.anim.Anim
import com.foureyedstraighthair.ticks.jam.helper.FloatPropertyName
import com.foureyedstraighthair.ticks.jam.helper.TranslationValueType
import com.foureyedstraighthair.ticks.jam.inline.convenience.InlineTranslateAnim

class TranslateAnim(
    jam: Jam, definition: InlineTranslateAnim)
    : Anim(jam, definition) {

    private val startFraction_x = definition.startFraction_x
    private val startFraction_y = definition.startFraction_y
    private val endFraction_x = definition.endFraction_x
    private val endFraction_y = definition.endFraction_y
    private val startDimen_x = definition.startDimen_x
    private val startDimen_y = definition.startDimen_y
    private val endDimen_x = definition.endDimen_x
    private val endDimen_y = definition.endDimen_y
    private val isStartBasedOnparent_x = definition.isStartBasedOnparent_x
    private val isStartBasedOnparent_y = definition.isStartBasedOnparent_y
    private val isEndBasedOnparent_x = definition.isEndBasedOnparent_x
    private val isEndBasedOnparent_y = definition.isEndBasedOnparent_y
    private val startWithCurrentValue_x = definition.startWithCurrentValue_x
    private val startWithCurrentValue_y = definition.startWithCurrentValue_y
    private val startValueType_x = definition.startValueType_x
    private val startValueType_y = definition.startValueType_y
    private val endValueType_x = definition.endValueType_x
    private val endValueType_y = definition.endValueType_y


    override fun onCreateAnimator(target: View): Animator {

        val startX =
            if (startWithCurrentValue_x) FloatPropertyName.TRANSLATION_X.getValueOf(target)
            else when (startValueType_x) {
                TranslationValueType.DIMENSION -> startDimen_x
                TranslationValueType.PERCENTAGE ->
                    if (isStartBasedOnparent_x) (target.parent as ViewGroup).width * startFraction_x
                    else target.width * startFraction_x
            }

        val startY =
            if (startWithCurrentValue_y) FloatPropertyName.TRANSLATION_Y.getValueOf(target)
            else when (startValueType_y) {
                TranslationValueType.DIMENSION -> startDimen_y
                TranslationValueType.PERCENTAGE ->
                    if (isStartBasedOnparent_y) (target.parent as ViewGroup).height * startFraction_y
                    else target.height * startFraction_y
            }

        val endX =
            when (endValueType_x) {
                TranslationValueType.DIMENSION -> endDimen_x
                TranslationValueType.PERCENTAGE ->
                    if (isEndBasedOnparent_x) (target.parent as ViewGroup).width * endFraction_x
                    else target.width * endFraction_x
            }

        val endY =
            when (endValueType_y) {
                TranslationValueType.DIMENSION -> endDimen_y
                TranslationValueType.PERCENTAGE ->
                    if (isEndBasedOnparent_y) (target.parent as ViewGroup).height * endFraction_y
                    else target.height * endFraction_y
            }
        return ObjectAnimator.ofPropertyValuesHolder(target,
            PropertyValuesHolder.ofFloat(FloatPropertyName.TRANSLATION_X.camelCase, startX, endX),
            PropertyValuesHolder.ofFloat(FloatPropertyName.TRANSLATION_Y.camelCase, startY, endY))
    }
}