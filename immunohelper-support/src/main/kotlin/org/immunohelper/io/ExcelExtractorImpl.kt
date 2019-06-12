package org.immunohelper.io

import org.apache.logging.log4j.LogManager
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.immunohelper.io.model.Experience
import org.immunohelper.io.model.Node
import org.immunohelper.io.model.Sample
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileInputStream
import java.util.*


@Component
class ExcelExtractorImpl : ExtractorDataFile {

    companion object {
        val LOGGER = LogManager.getLogger(ExcelExtractorImpl::class)!!

        const val INDEX_COLUMN_SAMPLES = 0
        const val INDEX_ROW_CELLS = 0
        const val INDEX_SHEET = 0
        const val ROOT_TREE = "root"
    }

    override fun read(path: String?): Experience {

        val excelRows = extractRowsFromExcel(path)
        val mappingRowsSamples: MutableMap<Int, Sample> = mutableMapOf()
        val headerInfo: MutableMap<Int, String> = mutableMapOf()

        val parentTree = buildTreeBasedOnHeader(excelRows[0], headerInfo)

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

                                //TODO: Throw an exception
                                if (key != null) {
                                    cell.population.put(key, value)
                                }
                            }
                        }
                    }
                }

                println("$indexRow -> $indexColumn : ${excelRows[indexRow].getCell(indexColumn)}")
            }
        }

        return Experience.Builder()
                .withTreeCell(parentTree)
                .withSamplesInfo(mappingRowsSamples
                        .filterValues { true }
                        .map { sample -> sample.value.sampleName }
                        .toSet())
                .withHeadersInfo(headerInfo)
                .build()

    }

    //TODO: Add documentation
    private fun extractRowsFromExcel(path: String?): List<Row> {

        val excelFile = FileInputStream(File(path))
        val workbook = WorkbookFactory.create(excelFile)
        val sheet = workbook.getSheetAt(INDEX_SHEET)

        return sheet.rowIterator().iterator().asSequence().toList()

    }

    //TODO: CHange the documentation
    /**
     * Create tree based on header information
     *
     * @param header with the information to create the tree
     * @return the tree with all nodes but without any data, only contains the node names
     */
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
                    //TODO: throw an business exception
                }
            }
        }

        return root
    }

    /**
     * Get the header and split it, the delimiter needs to be "|" and "/".
     * E.g:
     *  //TODO: Complete documentation
     *
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