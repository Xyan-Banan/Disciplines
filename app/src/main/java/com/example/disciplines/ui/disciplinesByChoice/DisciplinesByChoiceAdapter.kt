package com.example.disciplines.ui.disciplinesByChoice

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disciplines.data.network.model.DisciplinesBundle
import com.example.disciplines.databinding.DisciplinesBundleBinding
import com.example.disciplines.ui.listUtils.AbstractListAdapter
import com.example.disciplines.ui.listUtils.Header
import com.example.disciplines.ui.listUtils.ViewHolder

class DisciplinesByChoiceAdapter(
    items: List<DisciplinesBundle>,
    header: Header,
    buttonListener: View.OnClickListener
) : AbstractListAdapter<DisciplinesBundle>(
    items,
    header,
    buttonListener
) {
    class DisciplinePairViewHolder private constructor(private val binding: DisciplinesBundleBinding) :
        ViewHolder<DisciplinesBundle>(binding) {

        init {
//            binding.radioGroup.setOnCheckedChangeListener { rg, checkedId ->
//                binding.disciplinesBundle?.checkedIndex =
//                    rg.indexOfChild(rg.findViewById(checkedId))
//            }
//            setIsRecyclable(false)
        }

        constructor(parent: ViewGroup) : this(
            DisciplinesBundleBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun bind(item: DisciplinesBundle) {
            binding.disciplinesBundle = item
        }
    }

    override fun createItemViewHolder(parent: ViewGroup) = DisciplinePairViewHolder(parent)
}