package com.foureyedstraighthair.ticks.jam.property

import com.foureyedstraighthair.ticks.jam.inline.InlineFloatProperty

class FloatProperty(definition: InlineFloatProperty)
    : Property<Float>(definition) {

    val startWithCurrentValue = definition.startWithCurrentValue
}