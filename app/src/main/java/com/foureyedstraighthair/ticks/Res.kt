package com.foureyedstraighthair.ticks

import android.content.Context
import android.support.v4.content.ContextCompat
import android.util.TypedValue



class Res(private val context: Context) {

    companion object {

        private const val COLOR_CACHE_ACCENT = -1

        private const val DIMEN_CACHE_NAVIGATION_BAR_HEIGHT = -1
    }

    private val colorsCache = mutableMapOf<Int, Int>()
    private val dimensCache = mutableMapOf<Int, Int>()

    fun navigationBarHeight(): Int {
        var height = dimensCache[DIMEN_CACHE_NAVIGATION_BAR_HEIGHT]
        if (height != null) return height
        val id = context.resources.getIdentifier(
            "navigation_bar_height", "dimen", "android")
        if (id > 0) {
            height = context.resources.getDimensionPixelSize(id)
            dimensCache[DIMEN_CACHE_NAVIGATION_BAR_HEIGHT] = height
            return height
        }

        return 0
    }

    fun pxOf(id: Int): Int {
        var px = dimensCache[id]
        if (px != null) return px
        px = context.resources.getDimensionPixelSize(id)
        dimensCache[id] = px
        return px
    }

    fun accentColor(): Int {
        var color = colorsCache[COLOR_CACHE_ACCENT]
        if (color != null) return color
        val typedValue = TypedValue()
        val a = context.obtainStyledAttributes(
            typedValue.data, intArrayOf(R.attr.colorAccent))
        color = a.getColor(0, 0)
        a.recycle()
        colorsCache[COLOR_CACHE_ACCENT] = color
        return color
    }

    fun colorOf(id: Int): Int {
        var color = colorsCache[id]
        if (color != null) return color
        color = ContextCompat.getColor(context, id)
        colorsCache[id] = color
        return color
    }
}