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
    val startValueType = definition.startValueType
    val endValueType = definition.endValueType

    override fun onCreateAnimator(target: View): Animator
            = ObjectAnimator.ofPropertyValuesHolder(target,
            PropertyValuesHolder.ofFloat(FloatPropertyName.TRANSLATION_X.camelCase,
                if (startWithCurrentValue.x) target.translationX else resolveStartX(target),
                resolveEndX(target)),
            PropertyValuesHolder.ofFloat(FloatPropertyName.TRANSLATION_Y.camelCase,
                if (startWithCurrentValue.y) target.translationY else resolveStartY(target),
                resolveEndY(target)))

    private fun resolveX(
        view: View,
        valueType: Vec<TranslationValueType>,
        valueFormat: Vec<TranslationValueFormat>,
        fraction: Vec<Float>,
        dimen: Vec<Float>,
        isBasedOnParent: Vec<Boolean>) = when (valueType.x) {

        TranslationValueType.ABSOLUTE -> when (valueFormat.x) {
            TranslationValueFormat.DIMENSION -> dimen.x
            TranslationValueFormat.PERCENTAGE ->
                if (isBasedOnParent.x) xOf(view) - (view.parent as ViewGroup).width * fraction.x
                else xOf(view) - view.width * fraction.x
        }

        TranslationValueType.RELATIVE -> when (valueFormat.x) {
            TranslationValueFormat.DIMENSION -> dimen.x
            TranslationValueFormat.PERCENTAGE ->
                if (isBasedOnParent.x) (view.parent as ViewGroup).width * fraction.x
                else view.width * fraction.x
        }
    }

    private fun resolveY(
        view: View,
        valueType: Vec<TranslationValueType>,
        valueFormat: Vec<TranslationValueFormat>,
        fraction: Vec<Float>,
        dimen: Vec<Float>,
        isBasedOnParent: Vec<Boolean>) = when (valueType.y) {

        TranslationValueType.ABSOLUTE -> when (valueFormat.y) {
            TranslationValueFormat.DIMENSION -> yOf(view) - dimen.y
            TranslationValueFormat.PERCENTAGE ->
                if (isBasedOnParent.y) yOf(view) - (view.parent as ViewGroup).height * fraction.y
                else yOf(view) - view.height * fraction.y
        }

        TranslationValueType.RELATIVE -> when (valueFormat.y) {
            TranslationValueFormat.DIMENSION -> dimen.y
            TranslationValueFormat.PERCENTAGE ->
                if (isBasedOnParent.y) (view.parent as ViewGroup).height * fraction.y
                else view.height * fraction.y
        }
    }

    private fun resolveStartX(view: View) = resolveX(
        view, startValueType, startValueFormat,
        startFraction, startDimen, isStartBasedOnParent)

    private fun resolveStartY(view: View) = resolveY(
        view, startValueType, startValueFormat,
        startFraction, startDimen, isStartBasedOnParent)

    private fun resolveEndX(view: View) = resolveX(
        view, endValueType, endValueFormat,
        endFraction, endDimen, isEndBasedOnParent)

    private fun resolveEndY(view: View) = resolveY(
        view, endValueType, endValueFormat,
        endFraction, endDimen, isEndBasedOnParent)

    private fun xOf(view: View) = view.x + view.pivotX

    private fun yOf(view: View) = view.y + view.pivotY
}