package org.immunohelper.io

import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.immunohelper.io.exception.InvalidDataFileException
import org.immunohelper.io.exception.InvalidFileException
import org.immunohelper.io.exception.InvalidHeaderFileException
import org.immunohelper.io.model.Experience
import org.immunohelper.io.model.Node
import org.immunohelper.io.model.Sample
import org.slf4j.LoggerFactory
import java.io.File
import java.io.FileInputStream
import java.util.*

/**
 * This implementation allows read .xsd files and extract the information
 * to the domain object Experience
 *
 *
 * @see Experience
 *
 * @since 1.0
 */
class ExcelExtractorImpl : ExtractorDataFile {

    private companion object {
        private val LOGGER = LoggerFactory.getLogger(ExcelExtractorImpl::class.java)

        private const val INDEX_COLUMN_SAMPLES = 0
        private const val INDEX_ROW_CELLS = 0
        private const val INDEX_SHEET = 0
        private const val ROOT_TREE = "root"
    }

    /**
     * Method to allow read a file
     *
     * @param path to file
     *
     * @return the data read from file
     *
     * @since 1.0
     */
    override fun read(path: String): Experience {

        try {

            val experienceBuilder: Experience.Builder = Experience.Builder()

            val excelRows = extractRowsFromExcel(path)

            val mappingRowsSamples: MutableMap<Int, Sample> = mutableMapOf()
            val headerInfo: MutableMap<Int, String> = mutableMapOf()

            val parentTree = buildTreeBasedOnHeader(excelRows[0], headerInfo)

            if (headerInfo.isNotEmpty()) {

                experienceBuilder.withIsValid(true)

                for (indexColumn in 0 until excelRows.first().lastCellNum) {
                    for (indexRow in 0 until excelRows.size) {

                        val column = excelRows[indexRow].getCell(indexColumn)

                        if (column != null) {
                            if (indexColumn == INDEX_COLUMN_SAMPLES && indexRow != INDEX_ROW_CELLS) {
                                mappingRowsSamples[indexRow] = Sample.Builder().build(column.stringCellValue)
                            } else {

                                if (indexColumn != INDEX_COLUMN_SAMPLES && indexRow != INDEX_ROW_CELLS) {

                                    val cell = parentTree.getChild(indexColumn)

                                    if (cell != null) {

                                        val value: Double = column.numericCellValue
                                        val key: Sample? = mappingRowsSamples[indexRow]

                                        if (key != null) {
                                            cell.population.put(key, value)
                                        } else {
                                            throw InvalidDataFileException("Can not find the correct key for value")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return experienceBuilder
                    .withTreeCell(parentTree)
                    .withSamplesInfo(mappingRowsSamples
                            .map { sample -> sample.value.sampleName }
                            .filterNotNull()
                            .toSet())
                    .withHeadersInfo(headerInfo)
                    .build()

        } catch (ex: Exception) {

            LOGGER.warn("operation='read', msg='Can not read the file ($path) because of:'", ex)
            return Experience.Builder()
                    .withIsValid(false)
                    .build()

        }

    }

    /**
     * Extract the rows from excel. This method it is responsible to catch the file and extract all rows
     *
     * @param path the path for file
     *
     * @return list with all rows inside the excel file
     *
     * @throws  InvalidFileException
     *          If the file of sometings is not correct
     */
    @Throws(InvalidFileException::class)
    private fun extractRowsFromExcel(path: String): List<Row> {

        try {
            val excelFile = FileInputStream(File(path))
            val workbook = WorkbookFactory.create(excelFile)
            val sheet = workbook.getSheetAt(INDEX_SHEET)

            //FIXME: Verify the asSequence() is a safety method
            return sheet.rowIterator().iterator().asSequence().toList()
        } catch (ex: Exception) {
            throw InvalidFileException("Can't read the file because of: ", ex)
        }

    }

    /**
     * Create the tree information based header's fields
     *
     * @param header row with the information to create the tree (headers)
     * @param headerInfo a copy header's fields. PAIR{NUM_COLUMN, HEADER}
     *
     * @return the tree with all nodes but without any data, only contains the node names and hierarchic structure
     *
     * @throws  InvalidHeaderFileException
     *          When not can get the correct headers from {@code headerInfo}
     */
    @Throws(InvalidHeaderFileException::class)
    private fun buildTreeBasedOnHeader(header: Row, headerInfo: MutableMap<Int, String>): Node {
        val root = Node()
        root.name = ROOT_TREE

        val columnIterator = header.cellIterator()

        while (columnIterator.hasNext()) {

            val cell = columnIterator.next()

            if (cell.columnIndex != INDEX_COLUMN_SAMPLES) {

                val cellsNames: Queue<String> = parseCellsNames(cell.stringCellValue)
                headerInfo[cell.columnIndex] = cell.stringCellValue

                if (cellsNames.isNotEmpty()) {
                    root.addChildren(cellsNames, cell.columnIndex)
                } else {
                    throw InvalidHeaderFileException("Can't find valid header!")
                }
            }
        }

        return root
    }

    /**
     * Get the header and split it, the delimiter "|" and "/".
     * <p>
     * E.g:
     *  Input: Lymphocytes/Single Cells/CD4/T-bet, CD4 subset | Freq. of Parent
     *  Result: Queue["Lymphocytes", "Single Cells", "CD4", "CD4 subset"]
     * </p>
     *
     * @param column with the cells names separated by "/"
     *
     * @return a queue with all cells entries
     */
    private fun parseCellsNames(column: String): Queue<String> {
        val cellVsType = column.split("|")
        val listOfNames = LinkedList<String>()

        if (cellVsType.size == 2) {
            listOfNames.addAll(cellVsType[0].split("/").map { name -> name.trim() })
        }

        return listOfNames
    }


}