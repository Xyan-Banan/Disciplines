package com.example.disciplines.ui.disciplinesByChoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disciplines.R
import com.example.disciplines.data.network.model.DisciplinesPair
import com.example.disciplines.databinding.DisciplinesPairBinding
import com.example.disciplines.ui.listUtils.AbstractListAdapter
import com.example.disciplines.ui.listUtils.Header
import com.example.disciplines.ui.listUtils.ViewHolder

class DisciplinesByChoiceAdapter(
    items: List<DisciplinesPair>,
    header: Header,
    buttonListener: View.OnClickListener
) : AbstractListAdapter<DisciplinesPair>(
    items,
    header,
    buttonListener
) {
    class DisciplinePairViewHolder private constructor(private val binding: DisciplinesPairBinding) :
        ViewHolder<DisciplinesPair>(binding) {

        init {
            binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
                when (checkedId) {
                    R.id.disciplineRadio1 -> binding.disciplinesPair?.checked =
                        DisciplinesPair.Checked.First
                    R.id.disciplineRadio2 -> binding.disciplinesPair?.checked =
                        DisciplinesPair.Checked.Second
                    else -> binding.disciplinesPair?.checked = DisciplinesPair.Checked.None
                }
            }
//            setIsRecyclable(false)
        }

        constructor(parent: ViewGroup) : this(
            DisciplinesPairBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun bind(item: DisciplinesPair) {
            binding.disciplinesPair = item
        }
    }

    override fun createItemViewHolder(parent: ViewGroup) = DisciplinePairViewHolder(parent)
}