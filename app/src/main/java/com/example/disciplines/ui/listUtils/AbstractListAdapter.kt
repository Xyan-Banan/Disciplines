package com.example.disciplines.ui.listUtils

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractListAdapter<T>(
    items: List<T>,
    private val header: Header,
    private val buttonListener: View.OnClickListener,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val values: List<ListItem> = addHeaderAndButton(items)

    private fun addHeaderAndButton(items: List<T>): List<ListItem> = buildList {
        add(ListItem.Header)
        if (items.isNotEmpty()) {
            addAll(items.map { ListItem.Item(it) })
            add(ListItem.Button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ListItemType.Header.ordinal -> HeaderViewHolder(parent, header)
            ListItemType.Item.ordinal -> createItemViewHolder(parent)
            else -> ButtonViewHolder(parent, buttonListener)
        }
    }

    abstract fun createItemViewHolder(parent: ViewGroup): ViewHolder<T>

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ListItemType.Header.ordinal
            values.lastIndex -> ListItemType.Button.ordinal
            else -> ListItemType.Item.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as? ViewHolder<T>)?.apply {
            val item = values[position] as ListItem.Item<T>
            bind(item.item)
        }

    }

    override fun getItemCount(): Int = values.size
}