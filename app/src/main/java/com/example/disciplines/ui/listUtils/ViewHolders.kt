package com.example.disciplines.ui.listUtils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.disciplines.R
import com.example.disciplines.data.network.model.DisciplinesPair
import com.example.disciplines.data.network.model.MobilityModule
import com.example.disciplines.databinding.*


class HeaderViewHolder private constructor(
    binding: ListHeaderBinding,
    text: String
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.instructions.text = text
    }

    constructor(parent: ViewGroup, text: String) : this(
        ListHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        text
    )
}

class ButtonViewHolder private constructor(
    binding: ListButtonBinding,
    listener: View.OnClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.button.setOnClickListener(listener)
    }

    constructor(parent: ViewGroup, listener: View.OnClickListener) : this(
        ListButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        listener
    )
}

abstract class ViewHolder<T>(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}

class DisciplinePairViewHolder private constructor(private val binding: DisciplinesPairBinding) :
    ViewHolder<DisciplinesPair>(binding) {

    init {
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.disciplineRadio1 -> binding.disciplinesPair?.checked =
                    DisciplinesPair.Checked.First
                R.id.disciplineRadio2 -> binding.disciplinesPair?.checked =
                    DisciplinesPair.Checked.Second
            }
        }
        setIsRecyclable(false)
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

class MobilityModuleViewHolder(binding: MobilityModuleItemBinding) :
    ViewHolder<MobilityModule>(binding) {
    constructor(parent: ViewGroup) : this(
        MobilityModuleItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun bind(item: MobilityModule) {

    }
}