package com.foureyedstraighthair.ticks.jam.inline.property

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.helper.ColorPropertyName

class InlineColorProperty(
    context: Context, attributeSet: AttributeSet)
    : InlineProperty<Int>(context, attributeSet) {

    override val startValue: Int
    override val endValue: Int
    override val name: String

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineColorProperty, 0, 0)

        startValue = attrs.getColor(
            R.styleable.InlineColorProperty_jam_startColor, 0)

        endValue = attrs.getColor(
            R.styleable.InlineColorProperty_jam_endColor, 0)

        val enum = attrs.getInt(R.styleable.InlineColorProperty_jam_colorProperty, 0)
        name = ColorPropertyName.findBy(enum, context)?.camelCase
                ?: throw RuntimeException("Unknown float property.")

        attrs.recycle()
    }
}