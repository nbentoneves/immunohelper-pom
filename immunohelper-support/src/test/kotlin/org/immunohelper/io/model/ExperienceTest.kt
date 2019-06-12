package org.immunohelper.io.model

import org.hamcrest.Matchers.allOf
import org.hamcrest.core.StringContains.containsString
import org.junit.Assert.assertThat
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ExperienceTest {

    @Test
    fun `Should create a experience and check toString method`() {

        val headerInfo: Map<Int, String> = mapOf(Pair(0, "Hello"), Pair(1, "Hello - 1"))
        val sampleInfo: Set<String> = setOf("Sample 0", "Sample 1")
        val node = Node()

        val experience = Experience.Builder()
                .withTreeCell(node)
                .withIsValid(true)
                .withHeadersInfo(headerInfo)
                .withSamplesInfo(sampleInfo)
                .build()

        assertNotNull(experience)
        assertEquals(headerInfo, experience.headersInfo)
        assertEquals(sampleInfo, experience.samplesInfo)
        assertNotNull(experience.parentNode)

        assertThat(experience.toString(), allOf(
                containsString("parentNode=$node"),
                containsString("headersInfo={0=Hello, 1=Hello - 1}"),
                containsString("samplesInfo=[Sample 0, Sample 1]"),
                containsString("valid=true")
        ))
    }

}