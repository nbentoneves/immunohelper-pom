package org.immunohelper.io

import org.immunohelper.io.model.Experience

interface ExtractorDataFile {

    fun read(path: String?): Experience

}