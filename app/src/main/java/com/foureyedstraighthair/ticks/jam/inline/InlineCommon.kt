package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.support.annotation.IdRes
import android.util.AttributeSet
import android.view.View
import com.foureyedstraighthair.ticks.Attrs
import com.foureyedstraighthair.ticks.R

open class InlineCommon(
    context: Context,
    attributeSet: AttributeSet)
    : View(context, attributeSet) {

    @IdRes
    val target: Int

    init {
        val attrs = Attrs(
            context,
            attributeSet,
            R.styleable.InlineCommon)

        target = attrs.fetchResourceID(
            R.styleable.InlineCommon_jam_target)

        attrs.recycle()

        visibility = View.GONE
        setWillNotDraw(false)
    }

    final override fun setWillNotDraw(willNotDraw: Boolean)
            = super.setWillNotDraw(willNotDraw)
}