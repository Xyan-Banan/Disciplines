package com.example.disciplines.ui.electives

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import com.example.disciplines.data.network.model.Elective
import com.example.disciplines.databinding.ElectiveItemBinding
import com.example.disciplines.ui.listUtils.AbstractListAdapter
import com.example.disciplines.ui.listUtils.Header
import com.example.disciplines.ui.listUtils.ViewHolder

class ElectivesAdapter(
    items: List<Elective>,
    header: Header,
    buttonListener: View.OnClickListener
) :
    AbstractListAdapter<Elective>(
        items, header,
        buttonListener
    ) {
    class ElectiveViewHolder(val binding: ElectiveItemBinding) : ViewHolder<Elective>(binding) {
        init {
            (binding.root as CheckBox).setOnCheckedChangeListener { _, isChecked ->
                binding.elective?.checked = isChecked
            }
        }

        constructor(parent: ViewGroup) : this(
            ElectiveItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun bind(item: Elective) {
            binding.elective = item
        }
    }

    override fun createItemViewHolder(parent: ViewGroup) = ElectiveViewHolder(parent)
}