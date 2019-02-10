package com.foureyedstraighthair.ticks

import org.junit.Test

import org.junit.Assert.*

class TimeTest {

    @Test
    fun of() {
        assertEquals(true, Time.of(3).equals(3, 0, 0, 0))
        assertEquals(true, Time.of(3, 45).equals(3, 45, 0, 0))
        assertEquals(true, Time.of(3, 45, 20).equals(3, 45, 20, 0))
        assertEquals(true, Time.of(3, 45, 20, 77).equals(3, 45, 20, 77))
        assertEquals(true, Time.of(13520077L).equals(3, 45, 20, 77))
        assertEquals(true, Time.of(-1000).equals(0, 0, 0, 0))
    }

    @Test
    fun zero() {
        assertEquals(true, Time.zero().equals(0, 0, 0, 0))
    }

    fun plus() {
        val t1 = Time.of(0, 0, 30)
        val t2 = Time.of(0, 59, 30)
        val t3 = t1 + t2
        assertEquals(true, t3.equals(1, 0, 0, 0))
    }

    fun minus() {
        val t1 = Time.of(1)
        val t2 = Time.of(0, 59, 30)
        val t3 = t1 - t2
        assertEquals(true, t3.equals(0, 0, 30, 0))

        val t4 = t2 - t1
        assertEquals(true, Time.zero())
    }
}