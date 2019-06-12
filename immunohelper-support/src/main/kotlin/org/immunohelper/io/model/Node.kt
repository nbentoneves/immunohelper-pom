package org.immunohelper.io.model

import java.util.*

class Node {

    var parent: Node = this
    var name: String? = null
    var easyAccess: Int = 0
    var population: MutableMap<Sample, Double> = mutableMapOf()
    var listOfChildren: MutableSet<Node> = mutableSetOf()

    fun addChildren(child: Node, easyAccess: Int = 0): Node {
        child.parent = this
        child.easyAccess = easyAccess
        this.listOfChildren.add(child)

        return child
    }

    fun addChildren(children: Queue<String>, easyAccess: Int): Node? {

        if (children.isEmpty()) {
            return this
        }

        return addChildren(children.remove(), children, this, easyAccess)
    }

    private fun addChildren(name: String, children: Queue<String>, node: Node, easyAccess: Int): Node? {

        var child = getChild(name, node)

        if (child == null) {
            val newCell = Node()
            newCell.name = name
            node.addChildren(newCell, easyAccess)
            child = newCell
        }

        if (children.isEmpty()) {
            return child
        }

        addChildren(children.poll(), children, child, easyAccess)

        return null

    }

    fun getChild(name: String): Node? {
        return getChild(name, this)
    }

    fun getChild(easyAccess: Int): Node? {
        return getChild(easyAccess, this)
    }

    private fun getChild(easyAccess: Int, node: Node): Node? {

        if (node.easyAccess == easyAccess) {
            return node
        } else {
            node.listOfChildren.forEach { child ->
                run {
                    val cell = getChild(easyAccess, child)

                    if (cell != null) {
                        return cell
                    }
                }
            }
        }

        return null
    }

    private fun getChild(name: String, node: Node): Node? {

        if (node.name.equals(name)) {
            return node
        } else {
            node.listOfChildren.forEach { child ->
                run {
                    val cell = getChild(name, child)

                    if (cell != null) {
                        return cell
                    }
                }
            }
        }

        return null
    }

    //TODO: Write a print deep function

    override fun equals(other: Any?): Boolean {
        if (this === other) {
            return true
        }
        if (javaClass != other?.javaClass) {
            return false
        }

        other as Node

        if (name != other.name) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        return name?.hashCode() ?: 0
    }

    override fun toString(): String {
        return "Node(name=$name, easyAccess=$easyAccess, listOfChildren=$listOfChildren)"
    }


}