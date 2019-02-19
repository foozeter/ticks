package com.foureyedstraighthair.ticks.jam.inline.anim

import android.content.Context
import android.util.AttributeSet

abstract class InlinePropertyAnim<T>(
    context: Context,
    attributeSet: AttributeSet,
    private val propertyClass: Class<T>)
    : InlineAnim(context, attributeSet) {

    val properties; get() = collectProperties()

    private fun collectProperties(): List<T>
            = mutableListOf<T>().apply {
        for (i in 0 until childCount) {
            val child = propertyClass.cast(getChildAt(i))
            if (child != null) add(child)
            else throw ClassCastException("Invalid child type")
        }
    }
}