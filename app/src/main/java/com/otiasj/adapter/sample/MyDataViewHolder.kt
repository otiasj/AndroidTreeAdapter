package com.otiasj.adapter.sample

import android.view.View
import android.widget.TextView
import com.otiasj.adapter.R
import com.otiasj.adapter.core.BaseViewHolder
import com.otiasj.adapter.core.DataChangeListener
import com.otiasj.adapter.core.Node

/**
 * Created by julien on 3/6/18.
 */

class MyDataViewHolder constructor(view: View) : BaseViewHolder(view) {

    private var label: TextView = view.findViewById(R.id.tv_label)
    private var name: TextView = view.findViewById(R.id.tv_name)

    override fun onBind(listener: DataChangeListener, node: Node) {
        super.onBind(listener, node)

        (node as? MyData)?.let { node ->
            label.text = node.index
            name.text = node.name
        }
    }

}
