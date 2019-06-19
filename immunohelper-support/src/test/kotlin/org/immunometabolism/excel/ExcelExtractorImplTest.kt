package org.immunometabolism.excel

import org.immunohelper.io.ExcelExtractorImpl
import org.immunohelper.io.ExtractorDataFile
import kotlin.test.Test

class ExcelExtractorImplTest {

    @Test
    fun `Simple test`() {

        val excel: ExtractorDataFile = ExcelExtractorImpl()

        excel.read(ExcelExtractorImplTest::class.java.getResource("/results.xls").path)
    }

}