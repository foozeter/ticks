package com.foureyedstraighthair.ticks.inlime

import android.content.Context
import android.support.annotation.AnimatorRes
import android.support.annotation.IdRes
import android.util.AttributeSet
import android.view.InflateException
import android.view.View
import com.foureyedstraighthair.ticks.R

class InlineAnimator(
    context: Context, attributeSet: AttributeSet)
    : View(context, attributeSet) {

    @IdRes
    val target: Int

    @IdRes
    val trigger: Int?

    @AnimatorRes
    val animatorSrc: Int

    val allowInterruption: Boolean

    val repeatAtInterruption: Boolean

    val triggeredByClick: Boolean

    val triggeredByLongClick: Boolean

    val stateName: String?

    val necessaryStateCondition: String?

    val nextState: String?

    init {

        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineAnimator, 0, 0)

        var id = attrs.getResourceId(R.styleable.InlineAnimator_inlime_target, 0)
        target = if (id > 0) id else throw InflateException("Attribute 'inlime_target' not found.")

        id = attrs.getResourceId(R.styleable.InlineAnimator_inlime_trigger, 0)
        trigger = if (id > 0) id else null

        id = attrs.getResourceId(R.styleable.InlineAnimator_inlime_animator, 0)
        animatorSrc = if (id > 0) id else throw InflateException("Attribute 'inlime_animatorSrc' not found.")

        allowInterruption = attrs.getBoolean(
            R.styleable.InlineAnimator_inlime_allowInterruption, true)

        repeatAtInterruption = attrs.getBoolean(
            R.styleable.InlineAnimator_inlime_reverseAtInterruption, false)

        triggeredByClick = attrs.getBoolean(
            R.styleable.InlineAnimator_inlime_triggeredByClick, false)

        triggeredByLongClick = attrs.getBoolean(
            R.styleable.InlineAnimator_inlime_triggeredByLongClick, false)

        stateName = attrs.getString(R.styleable.InlineAnimator_inlime_stateName)

        necessaryStateCondition = attrs.getString(R.styleable.InlineAnimator_inlime_necessaryStateCondition)

        nextState = attrs.getString(R.styleable.InlineAnimator_inlime_nextState)

        attrs.recycle()

        visibility = View.GONE
        setWillNotDraw(true)
    }
}