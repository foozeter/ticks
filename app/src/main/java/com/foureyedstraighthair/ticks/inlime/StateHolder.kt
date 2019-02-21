package com.foureyedstraighthair.ticks.inlime

data class StateHolder(
    private var value: String) {

    fun set(value: String) {
        this.value = value
    }

    fun get() = value

    operator fun invoke() = value

    fun isSameAs(value: String)
            = this.value == value
}