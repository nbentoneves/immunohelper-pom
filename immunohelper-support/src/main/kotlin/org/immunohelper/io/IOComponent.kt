package org.immunohelper.io

import org.immunohelper.io.model.Experience

open class IOComponent(private val excelExtractorImpl: ExtractorDataFile) {

    private var experience: Experience? = null

    fun getAllCells(): Set<String> {
        checkNotNull(experience) { "Should init the file firstly" }
        return this.experience!!.headersInfo.values.toSet()

    }

    fun getAllSamples(): Set<String> {
        checkNotNull(experience) { "Should init the file firstly" }
        return this.experience!!.samplesInfo
    }

    /**
     * Method to allow read the file only when the path change
     *
     * @param filePath path to the file
     *
     * @return the experience loaded
     *
     * @see ExtractorDataFile
     */
    fun loadExperience(filePath: String) {
        this.experience = excelExtractorImpl.read(filePath)
    }

}