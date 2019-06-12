package org.immunohelper.io

import org.immunohelper.io.model.Experience

/**
 * This implementation allows read files and extract the information
 * to the domain object Experience
 *
 * @see Experience
 *
 * @since 1.0
 */
interface ExtractorDataFile {

    /**
     * Method to allow read a file
     *
     * @param path to file
     *
     * @return the data read from file
     *
     * @since 1.0
     */
    fun read(path: String): Experience

}