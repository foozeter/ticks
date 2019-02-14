package com.foureyedstraighthair.ticks

import android.animation.ValueAnimator
import android.support.transition.Transition
import android.support.transition.TransitionValues
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class Recolor: Transition() {

    companion object {
        private const val PROP_TINT = "com.foureyedstraighthair.ticks:Recolor:tint"
    }

    private fun TransitionValues.getTint()
            = values[PROP_TINT] as Int

    private fun TransitionValues.setTint(tint: Int)
            = values.put(PROP_TINT, tint)

    private fun TransitionValues.hasTint(): Boolean {
        val value = values[PROP_TINT]
        return value != null && value is Int && value != TintImageView.TINT_NOT_SET
    }

    private fun canMakeAnimator(
        startValues: TransitionValues?,
        endValues: TransitionValues?)
            = startValues != null
            && endValues != null
            && startValues.hasTint()
            && endValues.hasTint()

    private fun makeAnimator(
        startValues: TransitionValues,
        endValues: TransitionValues,
        updateListener: (tint: Int, view: View) -> Unit)
            = ValueAnimator.ofArgb(
        startValues.getTint(),
        endValues.getTint()).apply {

        val view = startValues.view

        addUpdateListener {
            updateListener(it.animatedValue as Int, view)
        }
    }

    override fun captureStartValues(transitionValues: TransitionValues)
            = captureValues(transitionValues)

    override fun captureEndValues(transitionValues: TransitionValues)
            = captureValues(transitionValues)

    private fun captureValues(transitionValues: TransitionValues) {
        val view = transitionValues.view
        when (view) {
            is TintImageView -> transitionValues.setTint(view.tint)
            is TextView -> transitionValues.setTint(view.currentTextColor)
        }
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?)
            = when {

        !canMakeAnimator(startValues, endValues) -> null

        startValues!!.view is TintImageView ->
            makeAnimator(startValues, endValues!!) { tint, view ->
            (view as TintImageView).tint = tint
        }

        startValues.view is TextView ->
            makeAnimator(startValues, endValues!!) { tint, view ->
            (view as TextView).setTextColor(tint)
        }

        else -> null
    }
}