package org.immunohelper.io.model

import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class NodeTest {

    @Test
    fun `Should create a tree cell without parent`() {

        val simpleTree = Node()
        simpleTree.name = "Lymphocytes"

        assertNotNull(simpleTree)
        assertEquals("Lymphocytes", simpleTree.name)
        assertEquals(simpleTree, simpleTree.parent)

    }

    @Test
    fun `Should create a tree cell with parent and get child`() {

        val childTree = Node()
        childTree.name = "Single Cells"

        val mainTree = Node()
        mainTree.name = "Lymphocytes"
        mainTree.addChildren(childTree)

        assertNotNull(mainTree)
        assertEquals("Lymphocytes", mainTree.name)
        assertEquals(mainTree, mainTree.parent)
        assert(mainTree.listOfChildren.size == 1)
        assertEquals(childTree, mainTree.listOfChildren.first())
        assertEquals(mainTree, mainTree.getChild("Lymphocytes"))
        assertEquals(childTree, mainTree.getChild("Single Cells"))

    }

    @Test
    fun `Should create a tree cell with parent and multiple children and return correct child`() {

        val childLast = Node()
        childLast.name = "Effector cells"

        val childCd8Cell = Node()
        childCd8Cell.name = "CD8 T cells"
        childCd8Cell.addChildren(childLast)

        val childCd4Cell = Node()
        childCd4Cell.name = "CD4 T cells"

        val childTree = Node()
        childTree.name = "Single Cells"
        childTree.addChildren(childCd4Cell)
        childTree.addChildren(childCd8Cell)

        val mainTree = Node()
        mainTree.name = "Lymphocytes"
        mainTree.addChildren(childTree)

        assertNotNull(mainTree)
        assertEquals("Lymphocytes", mainTree.name)
        assertEquals(mainTree, mainTree.parent)
        assertEquals(mainTree, mainTree.getChild("Lymphocytes"))
        assertEquals(childTree, mainTree.getChild("Single Cells"))
        assertEquals(childLast, mainTree.getChild("Effector cells"))
        assertEquals(childCd4Cell, mainTree.getChild("CD4 T cells"))
        assertEquals(childCd8Cell, mainTree.getChild("CD8 T cells"))
        assertNull(mainTree.getChild("invalid cell"))

    }

    @Test
    fun `Should create a tree cell using list of cells`() {

        val listOfCells: Queue<String> = ArrayDeque<String>()
        listOfCells.add("Lymphocytes")
        listOfCells.add("Single Cells")
        listOfCells.add("CD4 T cells")

        val mainTree = Node()
        mainTree.name = "root"
        mainTree.addChildren(listOfCells, 0)

        assertNotNull(mainTree)
        assertEquals(1, mainTree.getChild("Lymphocytes")!!.listOfChildren.size)
        assertEquals(1, mainTree.getChild("Single Cells")!!.listOfChildren.size)
        assertEquals(0, mainTree.getChild("CD4 T cells")!!.listOfChildren.size)

    }

    @Test
    fun `Should add cells at tree already created, using list of cells`() {

        val listOfCells: Queue<String> = LinkedList<String>()
        listOfCells.add("Lymphocytes")
        listOfCells.add("Single Cells")
        listOfCells.add("CD4 T cells")

        val mainTree = Node()
        mainTree.name = "root"
        mainTree.addChildren(listOfCells, 0)

        val newTreeBranch: Queue<String> = LinkedList<String>()
        newTreeBranch.add("Single Cells")
        newTreeBranch.add("CD8 T cells")

        mainTree.addChildren(newTreeBranch, 1)

        assertNotNull(mainTree)
        assertEquals(0, mainTree.getChild("Single Cells")!!.easyAccess)
        assertEquals(2, mainTree.getChild("Single Cells")!!.listOfChildren.size)

        val newTreeBranch2: Queue<String> = LinkedList<String>()
        newTreeBranch2.add("Lymphocytes")
        newTreeBranch2.add("Complex Cells")

        mainTree.addChildren(newTreeBranch2, 2)

        assertNotNull(mainTree)
        assertEquals(0, mainTree.getChild("Lymphocytes")!!.easyAccess)
        assertEquals(2, mainTree.getChild("Complex Cells")!!.easyAccess)
        assertEquals(2, mainTree.getChild("Lymphocytes")!!.listOfChildren.size)
        assertEquals(2, mainTree.getChild("Single Cells")!!.listOfChildren.size)

        val newTreeBranchComplete: Queue<String> = LinkedList<String>()
        newTreeBranchComplete.add("Lymphocytes Other")
        newTreeBranchComplete.add("CD4 T cells")
        newTreeBranchComplete.add("Macro +1")

        mainTree.addChildren(newTreeBranchComplete, 3)

        assertNotNull(mainTree)
        assertEquals(3, mainTree.getChild("Macro +1")!!.easyAccess)
        assertEquals(2, mainTree.getChild("Lymphocytes")!!.listOfChildren.size)
        assertEquals(2, mainTree.getChild("Lymphocytes")!!.listOfChildren.size)
        assertEquals(1, mainTree.getChild("Lymphocytes Other")!!.listOfChildren.size)
        assertEquals(2, mainTree.getChild("Single Cells")!!.listOfChildren.size)


    }


}