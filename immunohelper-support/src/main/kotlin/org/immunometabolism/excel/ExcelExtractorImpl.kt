package org.immunometabolism.excel

import org.springframework.stereotype.Component

@Component
class ExcelExtractorImpl : ExcelExtractor {

    override fun readExcel() {
        println("Hello")
    }

}