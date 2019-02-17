package com.foureyedstraighthair.ticks.jam

import android.content.Context
import android.util.AttributeSet
import android.view.View

open class UndrawnView(
    context: Context,
    attributeSet: AttributeSet)
    : View(context, attributeSet) {

    init {
        visibility = View.GONE
        setWillNotDraw(false)
    }

    final override fun setWillNotDraw(willNotDraw: Boolean)
            = super.setWillNotDraw(willNotDraw)
}