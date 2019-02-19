package com.foureyedstraighthair.ticks.jam.inline.convenience

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.helper.TranslationValueType
import com.foureyedstraighthair.ticks.jam.inline.anim.InlineAnim
import kotlin.math.abs

class InlineTranslateAnim(
    context: Context, attributeSet: AttributeSet)
    : InlineAnim(context, attributeSet) {

    val startFraction_x: Float
    val startFraction_y: Float
    val endFraction_x: Float
    val endFraction_y: Float
    val startDimen_x: Float
    val startDimen_y: Float
    val endDimen_x: Float
    val endDimen_y: Float
    val isStartBasedOnparent_x: Boolean
    val isStartBasedOnparent_y: Boolean
    val isEndBasedOnparent_x: Boolean
    val isEndBasedOnparent_y: Boolean
    val startWithCurrentValue_x: Boolean
    val startWithCurrentValue_y: Boolean
    val startValueType_x: TranslationValueType
    val startValueType_y: TranslationValueType
    val endValueType_x: TranslationValueType
    val endValueType_y: TranslationValueType

    init {

        var fraction: Float
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineTranslateAnim, 0, 0)

        fraction = attrs.getFraction(R.styleable.InlineTranslateAnim_jam_startX, 1, -1, 0f)
        startFraction_x = abs(fraction)
        isStartBasedOnparent_x = fraction < 0f

        fraction = attrs.getFraction(R.styleable.InlineTranslateAnim_jam_startY, 1, -1, 0f)
        startFraction_y = abs(fraction)
        isStartBasedOnparent_y = fraction < 0f

        fraction = attrs.getFraction(R.styleable.InlineTranslateAnim_jam_endX, 1, -1, 0f)
        endFraction_x = abs(fraction)
        isEndBasedOnparent_x = fraction < 0f

        fraction = attrs.getFraction(R.styleable.InlineTranslateAnim_jam_endY, 1, -1, 0f)
        endFraction_y = abs(fraction)
        isEndBasedOnparent_y = fraction < 0f

        startDimen_x = attrs.getDimensionPixelSize(
            R.styleable.InlineTranslateAnim_jam_startXDimen, 0).toFloat()

        startDimen_y = attrs.getDimensionPixelSize(
            R.styleable.InlineTranslateAnim_jam_startYDimen, 0).toFloat()

        endDimen_x = attrs.getDimensionPixelSize(
            R.styleable.InlineTranslateAnim_jam_endXDimen, 0).toFloat()

        endDimen_y = attrs.getDimensionPixelSize(
            R.styleable.InlineTranslateAnim_jam_endYDimen, 0).toFloat()

        startWithCurrentValue_x = attrs.getBoolean(
            R.styleable.InlineTranslateAnim_jam_startWithCurrentX, false)

        startWithCurrentValue_y = attrs.getBoolean(
            R.styleable.InlineTranslateAnim_jam_startWithCurrentY, false)

        startValueType_x = TranslationValueType.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_startXType, 0), context)
                ?: TranslationValueType.PERCENTAGE

        startValueType_y = TranslationValueType.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_startYType, 0), context)
                ?: TranslationValueType.PERCENTAGE

        endValueType_x = TranslationValueType.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_endXType, 0), context)
                ?: TranslationValueType.PERCENTAGE

        endValueType_y = TranslationValueType.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_endYType, 0), context)
                ?: TranslationValueType.PERCENTAGE

        attrs.recycle()
    }
}