package com.foureyedstraighthair.ticks.jam.constant

import com.foureyedstraighthair.ticks.Attrs

enum class TriggerEvent(
    override val id: Int)
    : Attrs.IdentifiableEnum {
    ON_CLICK(0),
    ON_LONG_CLICK(1)
}