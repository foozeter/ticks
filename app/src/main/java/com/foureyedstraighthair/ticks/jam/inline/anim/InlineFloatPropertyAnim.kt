package com.foureyedstraighthair.ticks.jam.inline.anim

import android.content.Context
import android.util.AttributeSet
import com.foureyedstraighthair.ticks.jam.inline.property.InlineFloatProperty

class InlineFloatPropertyAnim(
    context: Context,
    attributeSet: AttributeSet)
    : InlinePropertyAnim<InlineFloatProperty>(
    context,
    attributeSet,
    InlineFloatProperty::class.java)