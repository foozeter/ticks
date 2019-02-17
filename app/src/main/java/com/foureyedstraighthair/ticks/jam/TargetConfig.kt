package com.foureyedstraighthair.ticks.jam

import android.content.Context
import android.support.annotation.IdRes
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.Attrs
import com.foureyedstraighthair.ticks.R

class TargetConfig(
    context: Context,
    attributeSet: AttributeSet)
    : UndrawnView(context, attributeSet) {

    @IdRes
    val target: Int

    val defaultFlag: Int

    init {
        val attrs = Attrs(
            context,
            attributeSet,
            R.styleable.TargetConfig)

        target = attrs.fetchResourceID(
            R.styleable.TargetConfig_jam_target_id)

        defaultFlag = attrs.fetchInt(
            R.styleable.TargetConfig_jam_default_flag, 0)

        attrs.recycle()
    }
}