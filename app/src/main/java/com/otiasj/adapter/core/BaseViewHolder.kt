package com.otiasj.adapter.core

import android.support.annotation.CallSuper
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by julien on 3/6/18.
 */

abstract class BaseViewHolder(open val view: View) : RecyclerView.ViewHolder(view) {

    private var dataChangeListener: DataChangeListener? = null
    private var node: Node? = null

    //pre allocate this
    val clickListener: View.OnClickListener = View.OnClickListener {
        node?.let { sectionData ->
            sectionData.load(dataChangeListener)
        }
    }

    @CallSuper
    open fun onBind(listener: DataChangeListener, node: Node) {
        this.dataChangeListener = listener
        this.node = node
        view.setOnClickListener(this.clickListener)
    }

    @CallSuper
    open fun recycle() {
        this.dataChangeListener = null
        this.node = null //Do we need to propagate this to the data? e.g cancel request??
    }
}
