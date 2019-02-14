package com.foureyedstraighthair.ticks

import android.content.Context
import android.util.TypedValue



class Res(private val context: Context) {

    fun fetchAccentColor(): Int {
        val typedValue = TypedValue()
        val a = context.obtainStyledAttributes(
            typedValue.data, intArrayOf(R.attr.colorAccent))
        val color = a.getColor(0, 0)
        a.recycle()
        return color
    }
}