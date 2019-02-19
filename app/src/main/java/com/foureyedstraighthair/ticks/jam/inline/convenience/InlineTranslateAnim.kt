package com.foureyedstraighthair.ticks.jam.inline.convenience

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.Default
import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.convenience.TranslateAnim
import com.foureyedstraighthair.ticks.jam.helper.TranslationValueFormat
import com.foureyedstraighthair.ticks.jam.helper.TranslationValueType
import com.foureyedstraighthair.ticks.jam.helper.Vec
import com.foureyedstraighthair.ticks.jam.inline.anim.InlineAnim
import kotlin.math.abs

class InlineTranslateAnim(
    context: Context, attributeSet: AttributeSet)
    : InlineAnim(context, attributeSet) {

    val startFraction: Vec<Float>
    val endFraction: Vec<Float>
    val startDimen: Vec<Float>
    val endDimen: Vec<Float>
    val isStartBasedOnParent: Vec<Boolean>
    val isEndBasedOnParent: Vec<Boolean>
    val startWithCurrentValue: Vec<Boolean>
    val startValueFormat: Vec<TranslationValueFormat>
    val endValueFormat: Vec<TranslationValueFormat>
    val valueType: Vec<TranslationValueType>

    init {

        var fraction: Float
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineTranslateAnim, 0, 0)

        fraction = attrs.getFraction(R.styleable.InlineTranslateAnim_jam_startX, 1, -1, 0f)
        val startFraction_x = abs(fraction)
        val isStartBasedOnparent_x = fraction < 0f

        fraction = attrs.getFraction(R.styleable.InlineTranslateAnim_jam_startY, 1, -1, 0f)
        val startFraction_y = abs(fraction)
        val isStartBasedOnparent_y = fraction < 0f

        fraction = attrs.getFraction(R.styleable.InlineTranslateAnim_jam_endX, 1, -1, 0f)
        val endFraction_x = abs(fraction)
        val isEndBasedOnparent_x = fraction < 0f

        fraction = attrs.getFraction(R.styleable.InlineTranslateAnim_jam_endY, 1, -1, 0f)
        val endFraction_y = abs(fraction)
        val isEndBasedOnparent_y = fraction < 0f

        val startDimen_x = attrs.getDimensionPixelSize(
            R.styleable.InlineTranslateAnim_jam_startXd, 0).toFloat()

        val startDimen_y = attrs.getDimensionPixelSize(
            R.styleable.InlineTranslateAnim_jam_startYd, 0).toFloat()

        val endDimen_x = attrs.getDimensionPixelSize(
            R.styleable.InlineTranslateAnim_jam_endXd, 0).toFloat()

        val endDimen_y = attrs.getDimensionPixelSize(
            R.styleable.InlineTranslateAnim_jam_endYd, 0).toFloat()

        val startWithCurrentValue_x = attrs.getBoolean(
            R.styleable.InlineTranslateAnim_jam_startWithCurrentX, false)

        val startWithCurrentValue_y = attrs.getBoolean(
            R.styleable.InlineTranslateAnim_jam_startWithCurrentY, false)

        val startValueFormat_x = TranslationValueFormat.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_startXFormat, 0), context)
                ?: Default.TRANSLATION_VALUE_FROMAT

        val startValueFormat_y = TranslationValueFormat.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_startYFormat, 0), context)
                ?: Default.TRANSLATION_VALUE_FROMAT

        val endValueFormat_x = TranslationValueFormat.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_endXFormat, 0), context)
                ?: Default.TRANSLATION_VALUE_FROMAT

        val endValueFormat_y = TranslationValueFormat.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_endYFormat, 0), context)
                ?: Default.TRANSLATION_VALUE_FROMAT

        val valueType_x = TranslationValueType.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_xType, 0), context)
                ?: Default.TRANSLATION_VALUE_TYPE

        val valueType_y = TranslationValueType.findBy(
            attrs.getInt(R.styleable.InlineTranslateAnim_jam_yType, 0), context)
                ?: Default.TRANSLATION_VALUE_TYPE

        attrs.recycle()

        startFraction = Vec(startFraction_x, startFraction_y)
        endFraction = Vec(endFraction_x, endFraction_y)
        startDimen = Vec(startDimen_x, startDimen_y)
        endDimen = Vec(endDimen_x, endDimen_y)
        isStartBasedOnParent = Vec(isStartBasedOnparent_x, isStartBasedOnparent_y)
        isEndBasedOnParent = Vec(isEndBasedOnparent_x, isEndBasedOnparent_y)
        startWithCurrentValue = Vec(startWithCurrentValue_x, startWithCurrentValue_y)
        startValueFormat = Vec(startValueFormat_x, startValueFormat_y)
        endValueFormat = Vec(endValueFormat_x, endValueFormat_y)
        valueType = Vec(valueType_x, valueType_y)
    }

    override fun makeAnim(jam: Jam)
            = TranslateAnim(jam, this)
}