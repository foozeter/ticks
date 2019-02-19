package com.foureyedstraighthair.ticks

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

/**
 * This class was created for com.foureyedstraighthair.ticks.Recolor class.
 */
class TintImageView(
    context: Context, attrs: AttributeSet)
    : ImageView(context, attrs) {

    companion object {
        const val TINT_NOT_SET = -1
    }

    /**
     * When tint the image, use this to animate changing findBy its color.
     * Then, try: TransitionManager.beginDelayedTransition(..., Recolor())
     */
    var tint = TINT_NOT_SET
        set(value) {
            field = value
            setColorFilter(value)
        }
}