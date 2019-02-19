package com.foureyedstraighthair.ticks.jam.helper

import android.content.Context
import android.view.View
import com.foureyedstraighthair.ticks.Attrs
import com.foureyedstraighthair.ticks.R

enum class FloatPropertyName(
    override val id: Int)
    : Attrs.IdentifiableEnum {

    TRANSLATION_X(R.integer.enum_floatProperty_translationX),
    TRANSLATION_Y(R.integer.enum_floatProperty_translationY),
    ROTATION(R.integer.enum_floatProperty_rotation),
    ROTATION_X(R.integer.enum_floatProperty_rotationX),
    ROTATION_Y(R.integer.enum_floatProperty_rotationY),
    SCALE_X(R.integer.enum_floatProperty_scaleX),
    SCALE_Y(R.integer.enum_floatProperty_scaleY),
    ALPHA(R.integer.enum_floatProperty_alpha),
    X(R.integer.enum_floatProperty_x),
    Y(R.integer.enum_floatProperty_y);

    val camelCase = name.toLowerCase().snakeToCamel()

    fun getValueOf(view: View) = when (this) {
        TRANSLATION_X -> view.translationX
        TRANSLATION_Y -> view.translationY
        ROTATION -> view.rotation
        ROTATION_X -> view.rotationX
        ROTATION_Y -> view.rotationY
        SCALE_X -> view.scaleX
        SCALE_Y -> view.scaleY
        ALPHA -> view.alpha
        X -> view.x
        Y -> view.y
    }

    companion object {

        fun findBy(camelCase: String)
                = values().find { it.camelCase == camelCase }

        fun findBy(integer: Int, context: Context)
         = values().find { context.resources.getInteger(it.id) == integer }

        fun getValueOf(view: View, camelCase: String) =
            FloatPropertyName.findBy(camelCase)?.getValueOf(view)
                ?: throw RuntimeException("Unknown property name: $camelCase")

    }
}