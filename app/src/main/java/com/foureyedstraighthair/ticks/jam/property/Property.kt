package com.foureyedstraighthair.ticks.jam.property

import com.foureyedstraighthair.ticks.jam.inline.InlineProperty

abstract class Property<T>(
    definition: InlineProperty<T>) {

    val startValue = definition.startValue
    val endValue = definition.endValue
    val name = definition.name
}