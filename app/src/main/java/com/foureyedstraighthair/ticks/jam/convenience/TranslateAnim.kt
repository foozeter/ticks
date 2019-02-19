package com.foureyedstraighthair.ticks.jam.convenience

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.ViewGroup
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.anim.Anim
import com.foureyedstraighthair.ticks.jam.helper.FloatPropertyName
import com.foureyedstraighthair.ticks.jam.helper.TranslationValueFormat
import com.foureyedstraighthair.ticks.jam.helper.TranslationValueType
import com.foureyedstraighthair.ticks.jam.helper.Vec
import com.foureyedstraighthair.ticks.jam.inline.convenience.InlineTranslateAnim

class TranslateAnim(
    jam: Jam, definition: InlineTranslateAnim)
    : Anim(jam, definition) {

    val startFraction = definition.startFraction
    val endFraction = definition.endFraction
    val startDimen = definition.startDimen
    val endDimen = definition.endDimen
    val isStartBasedOnParent = definition.isStartBasedOnParent
    val isEndBasedOnParent = definition.isEndBasedOnParent
    val startWithCurrentValue = definition.startWithCurrentValue
    val startValueFormat = definition.startValueFormat
    val endValueFormat = definition.endValueFormat

    val propertyName = Vec(
        when (definition.valueType.x) {
            TranslationValueType.ABSOLUTE -> FloatPropertyName.X
            TranslationValueType.RELATIVE -> FloatPropertyName.TRANSLATION_X
        },
        when (definition.valueType.y) {
            TranslationValueType.ABSOLUTE -> FloatPropertyName.Y
            TranslationValueType.RELATIVE -> FloatPropertyName.TRANSLATION_Y
        })

    override fun onCreateAnimator(target: View): Animator
            = ObjectAnimator.ofPropertyValuesHolder(target,
            PropertyValuesHolder.ofFloat(propertyName.x.camelCase,
                resolveStartX(target), resolveEndX(target)),
            PropertyValuesHolder.ofFloat(propertyName.y.camelCase,
                resolveStartY(target), resolveEndY(target)))

    private fun resolveX(
        view: View,
        valueFormat: Vec<TranslationValueFormat>,
        fraction: Vec<Float>,
        dimen: Vec<Float>,
        isBasedOnParent: Vec<Boolean>) = when (valueFormat.x) {
        TranslationValueFormat.DIMENSION -> dimen.x
        TranslationValueFormat.PERCENTAGE ->
            resolveBaseWidth(view, isBasedOnParent) * fraction.x
    }

    private fun resolveY(
        view: View,
        valueFormat: Vec<TranslationValueFormat>,
        fraction: Vec<Float>,
        dimen: Vec<Float>,
        isBasedOnParent: Vec<Boolean>) = when (valueFormat.y) {
        TranslationValueFormat.DIMENSION -> dimen.y
        TranslationValueFormat.PERCENTAGE ->
            resolveBaseHeight(view, isBasedOnParent) * fraction.y
    }

    private fun resolveBaseWidth(view: View, isBasedOnParent: Vec<Boolean>) =
        if (isBasedOnParent.x) (view.parent as ViewGroup).width.toFloat()
        else view.width.toFloat()

    private fun resolveBaseHeight(view: View, isBasedOnParent: Vec<Boolean>) =
        if (isBasedOnParent.y) (view.parent as ViewGroup).height.toFloat()
        else view.height.toFloat()

    private fun resolveStartX(view: View) =
        if (startWithCurrentValue.x) view.translationX
        else resolveX(view, startValueFormat, startFraction,
            startDimen, isStartBasedOnParent)

    private fun resolveStartY(view: View) =
        if (startWithCurrentValue.y) view.translationY
        else resolveY(view, startValueFormat, startFraction,
            startDimen, isStartBasedOnParent)

    private fun resolveEndX(view: View) = resolveX(
        view, endValueFormat, endFraction,
        endDimen, isEndBasedOnParent)

    private fun resolveEndY(view: View) = resolveY(
        view, endValueFormat, endFraction,
        endDimen, isEndBasedOnParent)
}