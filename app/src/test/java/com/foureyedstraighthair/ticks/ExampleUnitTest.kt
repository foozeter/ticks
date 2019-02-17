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
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun f() {
        val set = mutableSetOf(0, 1, 2, 3, 4, 5)
        set.forEach { if (it == 3) set.remove(it) }
    }

    @Test
    fun breadthFirstSearchTest() {

    }
}
