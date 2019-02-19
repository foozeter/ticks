package com.foureyedstraighthair.ticks.jam.helper

import android.content.Context
import com.foureyedstraighthair.ticks.R

enum class TranslationValueFormat(val id: Int) {

    PERCENTAGE(R.integer.enum_translationValueFormat_percentage),
    DIMENSION(R.integer.enum_translationValueFormat_dimension);

    companion object {

        fun findBy(integer: Int, context: Context)
                = values().find { context.resources.getInteger(it.id) == integer }
    }
}