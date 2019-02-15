package com.foureyedstraighthair.ticks

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.TypedValue



class Res(private val context: Context) {

    companion object {
        private const val CACHE_ACCENT_COLOR = -1
    }

    private val colorCache = mutableMapOf<Int, Int>()

    fun accentColor(): Int {
        var color = colorCache[CACHE_ACCENT_COLOR]
        if (color != null) return color
        val typedValue = TypedValue()
        val a = context.obtainStyledAttributes(
            typedValue.data, intArrayOf(R.attr.colorAccent))
        color = a.getColor(0, 0)
        a.recycle()
        colorCache[CACHE_ACCENT_COLOR] = color
        return color
    }

    fun colorOf(id: Int): Int {
        var color = colorCache[id]
        if (color != null) return color
        color = ContextCompat.getColor(context, id)
        colorCache[id] = color
        return color
    }
}