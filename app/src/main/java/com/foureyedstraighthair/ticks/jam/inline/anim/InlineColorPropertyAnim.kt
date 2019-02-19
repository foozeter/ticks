package com.foureyedstraighthair.ticks.jam.inline.anim

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.jam.inline.property.InlineColorProperty

class InlineColorPropertyAnim(
    context: Context, attributeSet: AttributeSet)
    : InlinePropertyAnim<InlineColorProperty>(
    context, attributeSet, InlineColorProperty::class.java)