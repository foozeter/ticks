package com.foureyedstraighthair.ticks.jam.helper

import android.content.Context
import com.foureyedstraighthair.ticks.Attrs
import com.foureyedstraighthair.ticks.R

enum class ColorPropertyName(
    override val id: Int)
    : Attrs.IdentifiableEnum {

    BACKGROUND_COLOR(R.integer.enum_colorProperty_backgroundColor),
    COLOR_FILTER(R.integer.enum_colorProperty_colorFilter),
    TEXT_COLOR(R.integer.enum_colorProperty_textColor);

    val camelCase = name.toLowerCase().snakeToCamel()

    companion object {

        fun findBy(integer: Int, context: Context)
                = values().find { context.resources.getInteger(it.id) == integer }
    }
}