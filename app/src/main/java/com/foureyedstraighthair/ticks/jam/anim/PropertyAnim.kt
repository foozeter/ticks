package com.foureyedstraighthair.ticks.jam.anim

import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.inline.InlinePropertyAnim

abstract class PropertyAnim(
    jam: Jam,
    definition: InlinePropertyAnim)
    : Anim(jam, definition) {

    val property = definition.property
}