package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorInt
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.R

class InlineColorPropertyAnim(
    context: Context,
    attributeSet: AttributeSet)
    : InlinePropertyAnim(context, attributeSet) {

    @ColorInt
    val startColor: Int

    @ColorInt
    val endColor: Int

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineColorPropertyAnim, 0, 0)

        startColor = attrs.getColor(
            R.styleable.InlineColorPropertyAnim_jam_startColor,
            Color.TRANSPARENT)

        endColor = attrs.getColor(
            R.styleable.InlineColorPropertyAnim_jam_endColor,
            Color.TRANSPARENT)

        attrs.recycle()
    }
}