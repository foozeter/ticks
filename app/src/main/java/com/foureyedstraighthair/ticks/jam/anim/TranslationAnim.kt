package com.foureyedstraighthair.ticks.jam.anim

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.view.View
import android.view.ViewGroup
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.constant.TranslationDelta
import com.foureyedstraighthair.ticks.jam.inline.InlineTranslationAnim

class TranslationAnim(
    jam: Jam,
    definition: InlineTranslationAnim)
    : Anim(jam, definition) {

    val fromXDelta = definition.fromXDelta
    val fromYDelta = definition.fromYDelta
    val toXDelta = definition.toXDelta
    val toYDelta = definition.toYDelta

    private val screenDensity
            = definition.context.resources.displayMetrics.density

    override fun onCreateAnimator(target: View): Animator {
        val fromX = resolveXDelta(fromXDelta, target)
        val fromY = resolveYDelta(fromYDelta, target)
        val toX = resolveXDelta(toXDelta, target)
        val toY = resolveYDelta(toYDelta, target)

        return when {

            fromX != toX && fromY != toY ->
                ObjectAnimator.ofPropertyValuesHolder(target,
                    PropertyValuesHolder.ofFloat("translationX", fromX, toX),
                    PropertyValuesHolder.ofFloat("translationY", fromY, toY))

            fromX != toX ->
                ObjectAnimator.ofFloat(target, "translationX", fromX, toX)

            fromY != toY ->
                ObjectAnimator.ofFloat(target, "translationY", fromY, toY)

            else -> ObjectAnimator()
        }
    }

    private fun resolveXDelta(delta: TranslationDelta, view: View)
            = when (delta.type) {
        TranslationDelta.Type.ABSOLUTE -> delta.value
        TranslationDelta.Type.FRACTION -> view.width * delta.value
        TranslationDelta.Type.FRACTION_P -> (view.parent as ViewGroup).width * delta.value
        TranslationDelta.Type.DIMEN_DP -> screenDensity * delta.value
    }

    private fun resolveYDelta(delta: TranslationDelta, view: View)
            = when (delta.type) {
        TranslationDelta.Type.ABSOLUTE -> delta.value
        TranslationDelta.Type.FRACTION -> view.height * delta.value
        TranslationDelta.Type.FRACTION_P -> (view.parent as ViewGroup).height * delta.value
        TranslationDelta.Type.DIMEN_DP -> screenDensity * delta.value
    }
}