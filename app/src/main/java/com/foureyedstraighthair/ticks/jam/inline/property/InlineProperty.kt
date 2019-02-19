package com.foureyedstraighthair.ticks.jam.inline.property

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.jam.inline.InlineBase

abstract class InlineProperty<T>(
    context: Context, attributeSet: AttributeSet)
    : InlineBase(context, attributeSet) {

    abstract val startValue: T
    abstract val endValue: T
    abstract val name: String
}