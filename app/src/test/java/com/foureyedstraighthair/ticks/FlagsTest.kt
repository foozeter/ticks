package com.foureyedstraighthair.ticks

import org.junit.Assert.assertEquals
import org.junit.Test

class FlagsTest {

    @Test
    fun has() {
        val flags = Flags(1)
        assertEquals(true, flags.has(1))
        assertEquals(false, flags.has(4))
        flags.set(5)
        assertEquals(true, flags.has(1))
        assertEquals(true, flags.has(4))
    }

    @Test
    fun plus() {
        val flags = Flags(0)
        assertEquals(1, flags + 1)
        flags.set(1)
        assertEquals(3, flags + 2)
    }

    @Test
    fun minus() {
        val flags = Flags(3)
        assertEquals(2, flags - 1)
        flags.set(2)
        assertEquals(2, flags - 1)
        assertEquals(0, flags - 2)
    }

    @Test
    fun plusAssign() {
        val flags = Flags()
        flags += 1
        assertEquals(1, flags.toInt())
        flags += 2
        assertEquals(3, flags.toInt())
    }

    @Test
    fun minusAssign() {
        val flags = Flags(3)
        flags -= 1
        assertEquals(2, flags.toInt())
        flags -= 1
        assertEquals(2, flags.toInt())
        flags -= 2
        assertEquals(0, flags.toInt())
    }
}