package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.R

class InlineFloatPropertyAnim(
    context: Context,
    attributeSet: AttributeSet)
    : InlinePropertyAnim(context, attributeSet) {

    val startValue: Float
    val endValue: Float

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineFloatPropertyAnim, 0, 0)

        startValue = attrs.getFloat(
            R.styleable.InlineFloatPropertyAnim_jam_startFloat, 0f)

        endValue = attrs.getFloat(
            R.styleable.InlineFloatPropertyAnim_jam_endFloat, 0f)

        attrs.recycle()
    }
}