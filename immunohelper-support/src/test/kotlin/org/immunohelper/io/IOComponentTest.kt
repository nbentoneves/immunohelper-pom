package org.immunohelper.io

import org.immunohelper.io.model.Experience
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import kotlin.test.*

class IOComponentTest {

    private companion object {
        private const val PATH = "path://"
    }

    @Mock
    lateinit var extractorDataFile: ExtractorDataFile

    lateinit var victim: IOComponent

    @BeforeTest
    internal fun setUp() {
        initMocks(this)
        victim = IOComponent(extractorDataFile)
    }

    @Test
    fun `Should test get cells`() {

        val experience = Experience.Builder()
                .withHeadersInfo(mapOf(Pair(0, "Cell_0"), Pair(1, "Cell_1"), Pair(2, "Cell_2")))
                .build()

        `when`(extractorDataFile.read(anyString())).thenReturn(experience)

        victim.loadExperience(PATH)

        val cells = victim.getAllCells()

        assertNotNull(cells)
        assertFalse { cells.isEmpty() }
        assertTrue { cells.contains("Cell_0") }
        assertTrue { cells.contains("Cell_1") }
        assertTrue { cells.contains("Cell_2") }

        verify(extractorDataFile, times(1)).read(anyString())

    }

    @Test
    fun `Should test get samples`() {

        val experience = Experience.Builder()
                .withSamplesInfo(setOf("Sample_0", "Sample_1", "Sample_2"))
                .build()

        `when`(extractorDataFile.read(anyString())).thenReturn(experience)

        victim.loadExperience(PATH)

        val cells = victim.getAllSamples()

        assertNotNull(cells)
        assertFalse { cells.isEmpty() }
        assertTrue { cells.contains("Sample_0") }
        assertTrue { cells.contains("Sample_1") }
        assertTrue { cells.contains("Sample_2") }

        verify(extractorDataFile, times(1)).read(anyString())

    }

}