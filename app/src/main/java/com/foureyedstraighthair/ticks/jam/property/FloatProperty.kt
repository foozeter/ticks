package com.foureyedstraighthair.ticks.jam.property

import com.foureyedstraighthair.ticks.jam.inline.property.InlineFloatProperty

class FloatProperty(definition: InlineFloatProperty)
    : Property<Float>(definition) {

    val startWithCurrentValue = definition.startWithCurrentValue
}