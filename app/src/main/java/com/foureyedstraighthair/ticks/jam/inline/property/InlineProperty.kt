package com.foureyedstraighthair.ticks.jam.inline.property

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.foureyedstraighthair.ticks.jam.property.Property

abstract class InlineProperty<T>(
    context: Context, attributeSet: AttributeSet)
    : View(context, attributeSet) {

    abstract val startValue: T
    abstract val endValue: T
    abstract val name: String

    init {
        visibility = View.GONE
        setWillNotDraw(false)
    }

    final override fun setWillNotDraw(willNotDraw: Boolean)
            = super.setWillNotDraw(willNotDraw)

    abstract fun makeProperty(): Property<T>
}