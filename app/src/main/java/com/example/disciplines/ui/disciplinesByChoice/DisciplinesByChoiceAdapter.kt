package com.example.disciplines.ui.disciplinesByChoice

import android.view.View
import android.view.ViewGroup
import com.example.disciplines.data.database.DisciplinesPair
import com.example.disciplines.ui.listUtils.*

class DisciplinesByChoiceAdapter(
    items: List<DisciplinesPair>,
    headerText: String,
    buttonListener: View.OnClickListener
) : AbstractListAdapter<DisciplinesPair>(
    items,
    headerText,
    buttonListener
) {

    private val values: List<ListItem> = addHeaderAndButton(items)

    private fun addHeaderAndButton(disciplines: List<DisciplinesPair>): List<ListItem> = buildList {
        add(ListItem.Header)
        if (disciplines.isNotEmpty()) {
            addAll(disciplines.map { ListItem.Item(it) })
            add(ListItem.Button)
        }
    }

    override fun getItemCount(): Int = values.size

    override fun createItemViewHolder(parent: ViewGroup): DisciplinePairViewHolder =
        DisciplinePairViewHolder(parent)
}