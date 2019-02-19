package com.foureyedstraighthair.ticks.jam.helper

import android.content.Context
import com.foureyedstraighthair.ticks.R

enum class TranslationValueType(val id: Int) {
    ABSOLUTE(R.integer.enum_translationValueType_absolute),
    RELATIVE(R.integer.enum_translationValueType_relative);

    companion object {

        fun findBy(integer: Int, context: Context)
                = values().find { context.resources.getInteger(it.id) == integer }
    }
}