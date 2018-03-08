package com.otiasj.adapter.core



interface Source : DataChangeListener {
    fun getItem(position: Int): Node
    fun getItemViewType(position: Int): Int
    fun getCount(): Int
}
