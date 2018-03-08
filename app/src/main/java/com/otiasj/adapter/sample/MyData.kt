package com.otiasj.adapter.sample

import com.otiasj.adapter.core.DataChangeListener
import com.otiasj.adapter.core.Node

/**
 * Created by julien on 3/7/18.
 * This class holds your implementation of Node, usually just a pointer to your data
 */

open class MyData : Node() {
    override fun hasChild(): Boolean {
        return (super.hasChild() && isOpen)
    }

    override fun getType(): Int {
        return type.type
    }

    var index: String = ""
    var name: String = ""
    internal var type: MyDataType = MyDataType.HEADER
    var isOpen: Boolean = false

    override fun load(listener: DataChangeListener?) {
        isOpen = !isOpen
        listener?.onDataChanged(this)
    }

}
