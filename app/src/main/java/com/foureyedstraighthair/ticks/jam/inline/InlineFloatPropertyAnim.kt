package com.foureyedstraighthair.ticks.jam.inline

import android.content.Context
import android.util.AttributeSet

class InlineFloatPropertyAnim(
    context: Context,
    attributeSet: AttributeSet)
    : InlinePropertyAnim<InlineFloatProperty>(
    context,
    attributeSet,
    InlineFloatProperty::class.java)