package com.magulab.test

import com.magulab.test.common.convertTimeStampToDateTime
import junit.framework.Assert.assertEquals
import org.junit.Test

class ExtensionTest {

    @Test
    fun `timestamp type should return date time string`() {
        val value: Long = 1590510966
        assertEquals("2020-05-26 16:36:06", value.convertTimeStampToDateTime())
    }
}