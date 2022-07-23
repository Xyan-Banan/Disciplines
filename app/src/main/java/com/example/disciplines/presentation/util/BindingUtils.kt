package com.example.disciplines.presentation.util

import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import com.example.disciplines.R
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.models.DisciplinesBundle
import com.example.disciplines.databinding.DisciplineItemBinding
import com.example.disciplines.databinding.DisciplinesBundleBinding
import com.example.disciplines.databinding.ElectiveItemBinding
import com.example.disciplines.databinding.MobilityModuleItemBinding

fun RadioGroup.setDisciplinesBundle(disciplinesBundle: DisciplinesBundle?) {
    disciplinesBundle?.let {
        it.list.forEach { discipline ->
            val btn = DisciplineItemBinding.inflate(
                LayoutInflater.from(context),
                this,
                false
            )

            btn.root.text = DisciplinesTextFormatter.from(discipline)

            addView(btn.root)
        }

        when (it.checkedIndex) {
            -1 -> clearCheck()
            else -> check(get(it.checkedIndex).id)
        }

        setOnCheckedChangeListener { group, checkedId ->
            disciplinesBundle.checkedIndex = group.indexOfChild(group.findViewById(checkedId))
        }
    }
}

fun LinearLayout.setDisciplines(disciplines: List<DisciplinesBundle>?) {
    val bundlesList = disciplines ?: return

    for (bundle in bundlesList.withIndex()) {
        val binding = DisciplinesBundleBinding.inflate(
            LayoutInflater.from(context),
            this,
            false
        )

        binding.bundleRG.setDisciplinesBundle(bundle.value)
        binding.blockNameTV.text = context.getString(R.string.blockName, bundle.index + 1)

        addView(binding.root)
    }
}

@BindingAdapter("electives")
fun LinearLayout.setElectives(electives: List<Discipline.Elective>?) {
    electives ?: return

    for (elective in electives) {
        val binding = ElectiveItemBinding.inflate(
            LayoutInflater.from(context),
            this,
            false
        )

        with(binding.root)
        {
            text = DisciplinesTextFormatter.from(elective)
            isChecked = elective.isSelected
            setOnCheckedChangeListener { _, isChecked ->
                elective.isSelected = isChecked
            }
        }

        addView(binding.root)
    }
}

@BindingAdapter("mobilityModules")
fun RadioGroup.setMobilityModules(list: List<Discipline.MobilityModule>?) {
    list?.forEach { item ->
        val btn = MobilityModuleItemBinding.inflate(
            LayoutInflater.from(context),
            this,
            false
        )
        btn.root.text = DisciplinesTextFormatter.from(item)
        addView(btn.root)
    }
}