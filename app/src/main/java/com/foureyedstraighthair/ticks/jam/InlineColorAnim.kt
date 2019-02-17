package com.foureyedstraighthair.ticks.jam

import android.content.Context
import android.graphics.Color
import android.support.annotation.ColorInt
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.Attrs
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.constant.ColorProperty

class InlineColorAnim(
    context: Context,
    attributeSet: AttributeSet)
    : InlineAnim(context, attributeSet) {

    @ColorInt
    val startColor: Int

    @ColorInt
    val endColor: Int

    val property: ColorProperty

    init {
        val attrs = Attrs(
            context,
            attributeSet,
            R.styleable.InlineColorAnim)

        startColor = attrs.fetchColor(
            R.styleable.InlineColorAnim_jam_startColor,
            Color.TRANSPARENT)

        endColor = attrs.fetchColor(
            R.styleable.InlineColorAnim_jam_endColor,
            Color.TRANSPARENT)

        property = attrs.fetchEnum(
            R.styleable.InlineColorAnim_jam_property,
            ColorProperty.BACKGROUND_COLOR,
            ColorProperty::class.java)
    }
}