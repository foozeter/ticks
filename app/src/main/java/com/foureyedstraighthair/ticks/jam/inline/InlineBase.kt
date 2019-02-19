package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

open class InlineBase(
    context: Context,
    attributeSet: AttributeSet)
    : ViewGroup(context, attributeSet) {

    init {
        visibility = View.GONE
        setWillNotDraw(false)
    }

    final override fun setWillNotDraw(willNotDraw: Boolean)
            = super.setWillNotDraw(willNotDraw)

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        // Do nothing.
    }
}