package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.R

abstract class InlinePropertyAnim(
    context: Context,
    attributeSet: AttributeSet)
    : InlineAnim(context, attributeSet) {

    val property: String?

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlinePropertyAnim, 0, 0)

        property = attrs.getString(R.styleable.InlinePropertyAnim_jam_property)

        attrs.recycle()
    }
}