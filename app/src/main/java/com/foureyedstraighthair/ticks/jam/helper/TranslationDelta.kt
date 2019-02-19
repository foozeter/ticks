package com.foureyedstraighthair.ticks.jam.helper

import android.content.res.TypedArray
import android.view.View
import android.view.ViewGroup

class TranslationDelta(
    private val fraction: Float,
    private val orientation: Orientation,
    private val basedOnParentSize: Boolean) {

    enum class Orientation {
        VERTICAL, HORIZONTAL
    }

    fun resolve(view: View) =
        if (basedOnParentSize) when (orientation) {
            Orientation.VERTICAL -> view.height * fraction
            Orientation.HORIZONTAL -> view.width * fraction
        } else when (orientation) {
            Orientation.VERTICAL -> (view.parent as ViewGroup).height * fraction
            Orientation.HORIZONTAL -> (view.parent as ViewGroup).width * fraction
        }

    companion object {

        fun fromAttribute(
            typedArray: TypedArray, index: Int, orientation: Orientation): TranslationDelta {
            val fraction = typedArray.getFraction(index, 1, -1, 0f)
            return if (0f <= fraction) TranslationDelta(fraction, orientation, false)
            else TranslationDelta(-fraction, orientation, true)
        }
    }
}