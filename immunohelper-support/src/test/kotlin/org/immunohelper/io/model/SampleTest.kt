package org.immunohelper.io.model

import org.hamcrest.core.StringContains
import org.junit.Assert.assertThat
import java.util.*
import kotlin.test.*

class SampleTest {

    @Test
    fun `Should create a sample and check toString method`() {

        val sample = Sample.Builder()
                .build("Hello")

        assertNotNull(sample)
        assertEquals("Hello", sample.sampleName)

        assertThat(sample.toString(),
                StringContains.containsString("sampleName=Hello"))
    }

    @Test
    fun `Should create a sample and check hashCode and equals method`() {

        val sample = Sample.Builder()
                .build("Hello")

        val sampleEqual = Sample.Builder()
                .build("Hello")

        val sampleDifferent = Sample.Builder()
                .build("Hello Other")

        assertEquals(sampleEqual, sample)
        assertNotEquals(sampleDifferent, sample)
        assertFalse(sample.equals(null))
        assertFalse(sample.equals("Hello"))

        assertEquals(Objects.hashCode(sample), sample.hashCode())

    }
}