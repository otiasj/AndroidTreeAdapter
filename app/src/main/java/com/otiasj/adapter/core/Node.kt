package com.otiasj.adapter.core

abstract class Node {

    internal var children: MutableList<Node>? = null

    open fun hasChild(): Boolean {
        return children?.isNotEmpty() ?: false
    }

    open fun getChildCount(): Int {
        return children?.size ?: 0
    }

    open fun addChild(child: Node) {
        if (children == null) {
            children = mutableListOf()
        }
        children?.add(child)
    }

    abstract fun load(listener: DataChangeListener?)
    abstract fun getType(): Int
}