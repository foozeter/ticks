package com.foureyedstraighthair.ticks

import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    @Test
    fun integerTest() {
        val integer = """0|[1-9][0-9]*""".toRegex()
        assertEquals(true, integer.matches("0"))
        assertEquals(true, integer.matches("1"))
        assertEquals(true, integer.matches("123"))
        assertEquals(true, integer.matches("100"))
        assertEquals(false, integer.matches("001"))
        assertEquals(false, integer.matches("00"))
        assertEquals(false, integer.matches("00.0"))
        assertEquals(false, integer.matches("infinity"))
    }

    @Test
    fun realTest() {
        val real = """(0|[1-9][0-9]*)(.[0-9]+)?""".toRegex()
        assertEquals(true, real.matches("0"))
        assertEquals(true, real.matches("0.0"))
        assertEquals(true, real.matches("1.0"))
        assertEquals(true, real.matches("3.14"))
        assertEquals(true, real.matches("256"))
        assertEquals(true, real.matches("0.00"))
        assertEquals(true, real.matches("0.005"))
        assertEquals(false, real.matches("00.005"))
        assertEquals(false, real.matches("10."))
        assertEquals(false, real.matches("infinity"))
        assertEquals(false, real.matches("24dp"))
    }

    @Test
    fun dimenTest() {
        val dimen = """(0|[1-9][0-9]*)(.[0-9]+)?di?p""".toRegex()
        assertEquals(true, dimen.matches("0dp"))
        assertEquals(true, dimen.matches("0dip"))
        assertEquals(true, dimen.matches("24.5dp"))
        assertEquals(true, dimen.matches("24.5dip"))
        assertEquals(true, dimen.matches("0.5dp"))
        assertEquals(true, dimen.matches("0.5dip"))
        assertEquals(false, dimen.matches("0.5diip"))
        assertEquals(false, dimen.matches("00.5dp"))
        assertEquals(false, dimen.matches("00.5"))
        assertEquals(false, dimen.matches("24"))
    }

    @Test
    fun fractionTest() {
        val fraction = """(0|[1-9][0-9]*)(.[0-9]+)?%""".toRegex()
        assertEquals(true, fraction.matches("314%"))
        assertEquals(true, fraction.matches("3.14%"))
        assertEquals(false, fraction.matches("3.14dp"))
        assertEquals(false, fraction.matches("3.14"))
    }

    @Test
    fun fractionPTest() {
        val fractionP = """(0|[1-9][0-9]*)(.[0-9]+)?%p""".toRegex()
        assertEquals(true, fractionP.matches("314%p"))
        assertEquals(true, fractionP.matches("3.14%p"))
        assertEquals(false, fractionP.matches("3.14dp"))
        assertEquals(false, fractionP.matches("3.14"))
        assertEquals(false, fractionP.matches("314%"))
        assertEquals(false, fractionP.matches("3.14%"))
    }
}
