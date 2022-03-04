package com.example.disciplines.ui.disciplinesByChoice

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disciplines.data.database.DisciplinesPair
import com.example.disciplines.ui.listUtils.*

class DisciplinesByChoiceAdapter(
    items: List<DisciplinesPair>,
    private val headerText: String,
    private val buttonListener: View.OnClickListener
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val values: List<ListItem> = addHeaderAndButton(items)

    private fun addHeaderAndButton(disciplines: List<DisciplinesPair>): List<ListItem> = buildList {
        add(ListItem.Header)
        if (disciplines.isNotEmpty()) {
            addAll(disciplines.map { ListItem.Item(it) })
            add(ListItem.Button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ListItemType.Header.ordinal -> HeaderViewHolder(parent, headerText)
            ListItemType.Item.ordinal -> DisciplinePairViewHolder(parent)
            else -> ButtonViewHolder(parent, buttonListener)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ListItemType.Header.ordinal
            values.lastIndex -> ListItemType.Button.ordinal
            else -> ListItemType.Item.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is DisciplinePairViewHolder -> {
                val item = values[position] as ListItem.Item<*>
                holder.bind(item.item as DisciplinesPair)
            }
        }
    }

    override fun getItemCount(): Int = values.size
}