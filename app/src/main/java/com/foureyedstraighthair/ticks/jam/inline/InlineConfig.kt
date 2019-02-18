package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.Attrs
import com.foureyedstraighthair.ticks.R

class InlineConfig(
    context: Context,
    attributeSet: AttributeSet)
    : InlineCommon(context, attributeSet) {

    val defaultFlag: Int

    init {
        val attrs = Attrs(
            context,
            attributeSet,
            R.styleable.InlineConfig)

        defaultFlag = attrs.fetchInt(
            R.styleable.InlineConfig_jam_defaultFlag, 0)

        attrs.recycle()
    }
}