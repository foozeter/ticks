package com.foureyedstraighthair.ticks.jam.inline.property

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.helper.FloatPropertyName

class InlineFloatProperty(
    context: Context, attributeSet: AttributeSet)
    : InlineProperty<Float>(context, attributeSet) {

    override val startValue: Float
    override val endValue: Float
    override val name: String

    val startWithCurrentValue: Boolean

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineFloatProperty, 0, 0)

        startWithCurrentValue = attrs.getBoolean(
            R.styleable.InlineFloatProperty_jam_startWithCurrentValue, false)

        startValue = attrs.getFloat(
            R.styleable.InlineFloatProperty_jam_startFloat, 0f)

        endValue = attrs.getFloat(
            R.styleable.InlineFloatProperty_jam_endFloat, 0f)

        val enum = attrs.getInt(R.styleable.InlineFloatProperty_jam_floatProperty, 0)
        name = FloatPropertyName.findBy(enum, context)?.camelCase
                ?: throw RuntimeException("Unknown color property.")

        attrs.recycle()
    }
}