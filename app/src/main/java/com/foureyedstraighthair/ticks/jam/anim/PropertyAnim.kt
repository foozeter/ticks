package com.foureyedstraighthair.ticks.jam.anim

import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.inline.anim.InlinePropertyAnim
import com.foureyedstraighthair.ticks.jam.inline.property.InlineProperty
import com.foureyedstraighthair.ticks.jam.property.Property

//abstract class PropertyAnim<T: InlineProperty<*>, U: InlinePropertyAnim<T>>(
abstract class PropertyAnim<Q, P: Property<Q>, T: InlineProperty<Q>, U: InlinePropertyAnim<T>>(
    jam: Jam,
    definition: U)
    : Anim(jam, definition) {

    val properties = mutableListOf<P>().apply {
        definition.properties.forEach {
            add(it.makeProperty() as P)
        }
    }.toList()
}