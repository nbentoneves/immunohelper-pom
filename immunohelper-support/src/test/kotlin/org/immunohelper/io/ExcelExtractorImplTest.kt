package org.immunohelper.io

import org.immunohelper.io.model.Sample
import java.io.File
import kotlin.test.*

class ExcelExtractorImplTest {

    private companion object {
        private const val INVALID_PATH = "data/invalid.xls"
        private const val EMPTY_FILE = "data/empty.xls"
        private const val VALID_FILE = "data/valid.xls"
        private const val INVALID_FILE = "data/invalid.txt"
    }

    private val victim = ExcelExtractorImpl()

    @Test
    fun `Should send invalid path`() {
        val filePath = ExcelExtractorImplTest::class.java.getResource(File.separator + INVALID_PATH).path

        val experience = victim.read(filePath)

        assertNotNull(experience)
        assertFalse(experience.valid)

    }

    @Test
    fun `Should read empty file`() {

        val filePath = ExcelExtractorImplTest::class.java.getResource(File.separator + EMPTY_FILE).path

        val experience = victim.read(filePath)

        assertNotNull(experience)
        assertFalse(experience.valid)

    }

    @Test
    fun `Should read invalid file`() {

        val filePath = ExcelExtractorImplTest::class.java.getResource(File.separator + INVALID_FILE).path

        val experience = victim.read(filePath)

        assertNotNull(experience)
        assertFalse(experience.valid)

    }

    @Test
    fun `Should read file`() {

        val filePath = ExcelExtractorImplTest::class.java.getResource(File.separator + VALID_FILE).path

        val experience = victim.read(filePath)

        assertNotNull(experience)

        assertTrue { experience.valid }

        assertEquals(16, experience.samplesInfo.size)
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK1.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK2.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK3.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK4.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK5.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK6.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK7.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK8.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK9.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_AMPK10.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_WT2.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_WT3.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 3 SLA IL-2_WT4.fcs") }
        assertTrue { experience.samplesInfo.contains("Staining 5 PMA iono_Unstained.fcs") }
        assertTrue { experience.samplesInfo.contains("Mean") }
        assertTrue { experience.samplesInfo.contains("SD") }

        assertEquals(6, experience.headersInfo.size)
        assertTrue { experience.headersInfo.containsKey(1) }
        assertEquals("Lymphocytes | Freq. of Parent", experience.headersInfo[1])
        assertTrue { experience.headersInfo.containsKey(2) }
        assertEquals("Lymphocytes/Single Cells | Freq. of Parent", experience.headersInfo[2])
        assertTrue { experience.headersInfo.containsKey(3) }
        assertEquals("Lymphocytes/Single Cells/CD4 | Freq. of Parent", experience.headersInfo[3])
        assertTrue { experience.headersInfo.containsKey(4) }
        assertEquals("Lymphocytes/Single Cells/CD4/Foxp3, CD4 subset | Freq. of Parent", experience.headersInfo[4])
        assertTrue { experience.headersInfo.containsKey(5) }
        assertEquals("Lymphocytes/Single Cells/CD4/RORgt, CD4 subset | Freq. of Parent", experience.headersInfo[5])
        assertTrue { experience.headersInfo.containsKey(6) }
        assertEquals("Lymphocytes/Single Cells/CD4/T-bet, CD4 subset | Freq. of Parent", experience.headersInfo[6])

        assertEquals(71.8, experience.parentNode!!.getChild(1)!!.population[Sample.Builder().build("Staining 3 SLA IL-2_AMPK1.fcs")])
        assertEquals(4.51, experience.parentNode!!.getChild(1)!!.population[Sample.Builder().build("SD")])

        assertEquals(98.8, experience.parentNode!!.getChild(2)!!.population[Sample.Builder().build("Staining 3 SLA IL-2_AMPK1.fcs")])
        assertEquals(0.49, experience.parentNode!!.getChild(2)!!.population[Sample.Builder().build("SD")])

        assertEquals(11.0, experience.parentNode!!.getChild(3)!!.population[Sample.Builder().build("Staining 3 SLA IL-2_AMPK1.fcs")])
        assertEquals(3.23, experience.parentNode!!.getChild(3)!!.population[Sample.Builder().build("SD")])

        assertEquals(0.68, experience.parentNode!!.getChild(4)!!.population[Sample.Builder().build("Staining 3 SLA IL-2_AMPK1.fcs")])
        assertEquals(6.43, experience.parentNode!!.getChild(4)!!.population[Sample.Builder().build("SD")])

        assertEquals(1.04, experience.parentNode!!.getChild(5)!!.population[Sample.Builder().build("Staining 3 SLA IL-2_AMPK1.fcs")])
        assertEquals(0.3, experience.parentNode!!.getChild(5)!!.population[Sample.Builder().build("SD")])

        assertEquals(0.66, experience.parentNode!!.getChild(6)!!.population[Sample.Builder().build("Staining 3 SLA IL-2_AMPK1.fcs")])
        assertEquals(1.56, experience.parentNode!!.getChild(6)!!.population[Sample.Builder().build("SD")])


    }

}