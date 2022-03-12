package com.example.disciplines.ui.disciplinesByChoice

import android.view.View
import android.view.ViewGroup
import com.example.disciplines.data.network.model.DisciplinesPair
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

    override fun createItemViewHolder(parent: ViewGroup): DisciplinePairViewHolder =
        DisciplinePairViewHolder(parent)
}