package com.foureyedstraighthair.ticks

open class Flags(private var flags: Int = 0) {

    companion object {

        const val BIT_01 = 0b1
        const val BIT_02 = 0b10
        const val BIT_03 = 0b100
        const val BIT_04 = 0b1000
        const val BIT_05 = 0b10000
        const val BIT_06 = 0b100000
        const val BIT_07 = 0b1000000
        const val BIT_08 = 0b10000000
        const val BIT_09 = 0b100000000
        const val BIT_10 = 0b1000000000
        const val BIT_11 = 0b10000000000
        const val BIT_12 = 0b100000000000
        const val BIT_13 = 0b1000000000000
        const val BIT_14 = 0b10000000000000
        const val BIT_15 = 0b100000000000000
        const val BIT_16 = 0b1000000000000000
        const val BIT_17 = 0b10000000000000000
        const val BIT_18 = 0b100000000000000000
        const val BIT_19 = 0b1000000000000000000
        const val BIT_20 = 0b10000000000000000000
        const val BIT_21 = 0b100000000000000000000
        const val BIT_22 = 0b1000000000000000000000
        const val BIT_23 = 0b10000000000000000000000
        const val BIT_24 = 0b100000000000000000000000
        const val BIT_25 = 0b1000000000000000000000000
        const val BIT_26 = 0b10000000000000000000000000
        const val BIT_27 = 0b100000000000000000000000000
        const val BIT_28 = 0b1000000000000000000000000000
        const val BIT_29 = 0b10000000000000000000000000000
        const val BIT_30 = 0b100000000000000000000000000000
        const val BIT_31 = 0b1000000000000000000000000000000
        const val BIT_32 = 0b10000000000000000000000000000000
    }

    fun set(flags: Int) {
        this.flags = flags
    }

    fun clear() = set(0)

    fun has(flag: Int)
            = flags and flag == flag

    fun has(flags: Flags)
            = has(flags.flags)

    operator fun plus(flags: Int)
            = this.flags or flags

    operator fun minus(flags: Int)
            = this.flags and flags.inv()

    operator fun plusAssign(flags: Int) {
        this.flags = plus(flags)
    }

    operator fun minusAssign(flags: Int) {
        this.flags = minus(flags)
    }

    operator fun plus(flags: Flags)
            = plus(flags.flags)

    operator fun minus(flags: Flags)
            = minus(flags.flags)

    operator fun plusAssign(flags: Flags)
            = plusAssign(flags.flags)

    operator fun minusAssign(flags: Flags)
            = minusAssign(flags.flags)

    fun add(flags: Int)
            = plusAssign(flags)

    fun remove(flags: Int)
            = minusAssign(flags)

    override fun toString()
            = flags.toString()

    fun toBinaryString(prefix: String = "")
            = "$prefix${Integer.toBinaryString(flags)}"

    fun toHexString(prefix: String = "")
            = "$prefix${Integer.toHexString(flags)}"

    fun toInt() = flags

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Flags) return false

        if (flags != other.flags) return false

        return true
    }

    override fun hashCode(): Int {
        return flags
    }

}