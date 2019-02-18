package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.R
import com.foureyedstraighthair.ticks.jam.constant.TranslationDelta

class InlineTranslationAnim(
    context: Context,
    attributeSet: AttributeSet)
    : InlineAnim(context, attributeSet) {

    val fromXDelta: TranslationDelta
    val fromYDelta: TranslationDelta
    val toXDelta: TranslationDelta
    val toYDelta: TranslationDelta

    init {
        val attrs = context.obtainStyledAttributes(
            attributeSet, R.styleable.InlineTranslationAnim, 0, 0)

        var symbol = attrs.getString(R.styleable.InlineTranslationAnim_jam_fromXDelta)
        fromXDelta = if (symbol == null) TranslationDelta(0f, TranslationDelta.Type.ABSOLUTE)
        else TranslationDelta.from(symbol) ?: throw RuntimeException("Unresolvable symbol: $symbol")

        symbol = attrs.getString(R.styleable.InlineTranslationAnim_jam_fromYDelta)
        fromYDelta = if (symbol == null) TranslationDelta(0f, TranslationDelta.Type.ABSOLUTE)
        else TranslationDelta.from(symbol) ?: throw RuntimeException("Unresolvable symbol: $symbol")

        symbol = attrs.getString(R.styleable.InlineTranslationAnim_jam_toXDelta)
        toXDelta = if (symbol == null) TranslationDelta(0f, TranslationDelta.Type.ABSOLUTE)
        else TranslationDelta.from(symbol) ?: throw RuntimeException("Unresolvable symbol: $symbol")

        symbol = attrs.getString(R.styleable.InlineTranslationAnim_jam_toYDelta)
        toYDelta = if (symbol == null) TranslationDelta(0f, TranslationDelta.Type.ABSOLUTE)
        else TranslationDelta.from(symbol) ?: throw RuntimeException("Unresolvable symbol: $symbol")

        attrs.recycle()
    }
}