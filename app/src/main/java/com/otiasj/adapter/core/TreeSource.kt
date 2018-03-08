package com.otiasj.adapter.core

import java.util.*

/**
 * Created by julien on 3/6/18.
 */
class TreeSource(private val root: Node) : Source {

    private var flattenTree: List<Node> = ArrayList()

    init {
        buildFlattenTree()
    }

    //Transform the tree to a list (ordered by parsing order)
    private fun buildFlattenTree() {
        val newTree: MutableList<Node> = mutableListOf()
        parseTree(newTree, root)
        newTree.removeAt(0)//remove root
        flattenTree = newTree
    }

    override fun onDataChanged(node: Node) {
        //we could do something smarter here (only refresh the part of the list impacted ... good enough for now
        buildFlattenTree()
    }

    override fun getItem(position: Int): Node {
        return flattenTree[position]
    }

    override fun getItemViewType(position: Int): Int {
        return flattenTree[position].getType()
    }

    override fun getCount(): Int {
        return flattenTree.size
    }

    private fun parseTree(flatTree: MutableList<Node>, root: Node) {
        flatTree.add(root)
        root.children?.let { children ->
            for (childIndex in children.indices) {
                val child = children[childIndex]
                if (child.hasChild()) {
                    parseTree(flatTree, child)
                } else {
                    flatTree.add(child)
                }
            }
        }
    }
}

