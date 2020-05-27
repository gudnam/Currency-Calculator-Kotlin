package com.magulab.test

import com.magulab.test.common.convertTimeStampToDateTime
import com.magulab.test.common.price
import junit.framework.Assert.assertEquals
import org.junit.Test

class ExtensionTest {

    @Test
    fun `timestamp type should convert date time string`() {
        val value: Long = 1590510966
        val excepted = "2020-05-26 16:36:06"
        assertEquals(excepted, value.convertTimeStampToDateTime())
    }

    @Test
    fun `float type number should convert price format`() {
        val value: Double = 1000000.05
        val excepted = "1,000,000.05"
        assertEquals(excepted, value.price())
    }
}