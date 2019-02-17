package com.foureyedstraighthair.ticks.jam.constant

import com.foureyedstraighthair.ticks.Attrs

enum class ColorProperty(
    override val id: Int)
    : Attrs.IdentifiableEnum {
    BACKGROUND_COLOR(0),
    DRAWABLE_TINT(1),
    TEXT_COLOR(2);

    override fun toString()
            = when (this) {
        BACKGROUND_COLOR -> "backgroundColor"
        DRAWABLE_TINT -> "colorFilter"
        TEXT_COLOR -> "textColor"
    }
}