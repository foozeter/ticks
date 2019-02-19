package com.foureyedstraighthair.ticks.jam.helper

import android.content.Context
import com.foureyedstraighthair.ticks.R

enum class TranslationValueType(val id: Int) {

    PERCENTAGE(R.integer.enum_translationValueType_percentage),
    DIMENSION(R.integer.enum_translationValueType_dimension);

    companion object {

        fun findBy(integer: Int, context: Context)
                = values().find { context.resources.getInteger(it.id) == integer }
    }
}