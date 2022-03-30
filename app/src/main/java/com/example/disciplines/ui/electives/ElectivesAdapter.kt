package com.example.disciplines.ui.electives

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.example.disciplines.data.network.model.DisciplineS
import com.example.disciplines.databinding.ElectiveItemBinding
import com.example.disciplines.ui.listUtils.AbstractListAdapter
import com.example.disciplines.ui.listUtils.Header
import com.example.disciplines.ui.listUtils.ViewHolder

class ElectivesAdapter(
    items: List<DisciplineS.Elective>,
    header: Header,
    buttonListener: View.OnClickListener
) :
    AbstractListAdapter<DisciplineS.Elective>(
        items, header,
        buttonListener
    ) {
    class ElectiveViewHolder(val binding: ElectiveItemBinding) :
        ViewHolder<DisciplineS.Elective>(binding) {
        init {
            (binding.root as CheckBox).setOnCheckedChangeListener { _, isChecked ->
                binding.elective?.isChecked = isChecked
            }
        }

        constructor(parent: ViewGroup) : this(
            ElectiveItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun bind(item: DisciplineS.Elective) {
            binding.elective = item
        }
    }

    override fun createItemViewHolder(parent: ViewGroup) = ElectiveViewHolder(parent)
}