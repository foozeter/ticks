package com.foureyedstraighthair.ticks.jam.constant

import com.foureyedstraighthair.ticks.Flags

class TriggerEvents(flags: Int = 0): Flags(flags) {

    companion object {
        private const val ON_CLICK = Flags.BIT_01
        private const val ON_LONG_CLICK = Flags.BIT_02
        val onClick = TriggerEvents(ON_CLICK)
        val onLongClick = TriggerEvents(ON_LONG_CLICK)
    }

    fun hasOnClick() = has(ON_CLICK)
    fun addOnClick() = add(ON_CLICK)
    fun removeOnClick() = remove(ON_CLICK)

    fun hasOnLongClick() = has(ON_LONG_CLICK)
    fun addOnLongClick() = add(ON_LONG_CLICK)
    fun removeOnLongClick() = remove(ON_LONG_CLICK)
}