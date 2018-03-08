package com.otiasj.adapter.core

import android.content.Context
import android.support.v7.widget.RecyclerView

/**
 * Created by julien on 3/6/18.
 * A sectioned recycler adapter
 */
abstract class BaseRecyclerAdapter<T : BaseViewHolder>(open val context: Context) : RecyclerView.Adapter<T>(), DataChangeListener {

    private lateinit var source: Source

    private var count: Int = 0

    fun setSource(source: Source) {
        this.source = source
        this.count = source.getCount()
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return source.getItemViewType(position)
    }

    override fun getItemCount(): Int {
        return count
    }

    override fun onDataChanged(node: Node) {
        source.onDataChanged(node)
        this.count = source.getCount()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: T, position: Int) {
        val section = source.getItem(position)
        holder.onBind(this, section)
    }

    override fun onViewRecycled(holder: T) {
        holder.recycle()
    }
}
