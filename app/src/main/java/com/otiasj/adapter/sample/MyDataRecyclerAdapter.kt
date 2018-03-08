package com.otiasj.adapter.sample

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.otiasj.adapter.R
import com.otiasj.adapter.core.BaseViewHolder
import com.otiasj.adapter.core.TableViewRecyclerAdapter

/**
 * Created by julien on 3/6/18.
 */

class MyDataRecyclerAdapter(override val context: Context) : TableViewRecyclerAdapter<BaseViewHolder>(context) {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): BaseViewHolder {
        return when (MyDataType.from(viewType)) {
            MyDataType.HEADER -> {
                MyDataViewHolder(LayoutInflater.from(context).inflate(R.layout.header_item, parent, false))
            }
            MyDataType.ROW -> {
                MyDataViewHolder(LayoutInflater.from(context).inflate(R.layout.section_item, parent, false))
            }
            MyDataType.FOOTER -> {
                MyDataViewHolder(LayoutInflater.from(context).inflate(R.layout.footer_item, parent, false))
            }
        }
    }

}