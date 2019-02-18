package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.util.AttributeSet
import android.view.View

open class UnDrawnView(
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