package com.example.disciplines.ui.listUtils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.disciplines.R
import com.example.disciplines.data.database.DisciplinesPair
import com.example.disciplines.databinding.DisciplinesByChoiceListItemFragmentBinding
import com.example.disciplines.databinding.ListButtonBinding
import com.example.disciplines.databinding.ListHeaderBinding

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

class DisciplinePairViewHolder private constructor(private val binding: DisciplinesByChoiceListItemFragmentBinding) :
    RecyclerView.ViewHolder(binding.root) {
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
        DisciplinesByChoiceListItemFragmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    fun bind(item: DisciplinesPair) {
        binding.disciplinesPair = item
    }
}

class ButtonViewHolder(binding: ListButtonBinding, listener: View.OnClickListener) :
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