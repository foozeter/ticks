package com.foureyedstraighthair.ticks.jam.anim

import com.foureyedstraighthair.ticks.jam.Jam
import com.foureyedstraighthair.ticks.jam.inline.anim.InlinePropertyAnim
import com.foureyedstraighthair.ticks.jam.inline.property.InlineProperty
import com.foureyedstraighthair.ticks.jam.property.Property

abstract class PropertyAnim<P, Q: Property<P>, R: InlineProperty<P>, S: InlinePropertyAnim<R>>(
    jam: Jam,
    definition: S,
    propertyClass: Class<Q>,
    inlinePropertyClass: Class<R>)
    : Anim(jam, definition) {

    val properties = mutableListOf<Q>().apply {
        val constructor = propertyClass.getConstructor(inlinePropertyClass)
        definition.properties.forEach {
            add(constructor.newInstance(it))
        }
    }.toList()
}