package com.example.disciplines.ui

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import com.example.disciplines.GroupNumberInfo
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.data.network.model.DisciplinesBundle
import com.example.disciplines.databinding.DisciplineItemBinding
import com.example.disciplines.databinding.DisciplinesBundleBinding
import com.example.disciplines.databinding.ElectiveItemBinding
import com.example.disciplines.databinding.MobilityModuleItemBinding

@BindingAdapter("discipline")
fun RadioButton.setDiscipline(discipline: Discipline.ByChoice?) {
    discipline?.let {
        text = resources.getString(R.string.discipline, it.name, it.intensity)
    }
}

@BindingAdapter("disciplinesBundle")
fun RadioGroup.setDisciplinesBundle(disciplinesBundle: DisciplinesBundle?) {
    disciplinesBundle?.let {
        it.list.forEach { discipline ->
            val btn = DisciplineItemBinding.inflate(
                LayoutInflater.from(context),
                this,
                false
            )

            btn.discipline = discipline

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

@BindingAdapter("disciplines")
fun LinearLayout.setDisciplines(disciplines: List<DisciplinesBundle>?) {
    val bundlesList = disciplines ?: return

    for(bundle in bundlesList.withIndex()) {
        val binding = DisciplinesBundleBinding.inflate(
            LayoutInflater.from(context),
            this,
            false
        )

        binding.disciplinesBundle = bundle.value
        binding.blockName = context.getString(R.string.blockName, bundle.index + 1)

        addView(binding.root)
    }
}

@BindingAdapter("electives")
fun LinearLayout.setElectives(electives: List<Discipline.Elective>?) {
    electives ?: return

    for(elective in electives) {
        val binding = ElectiveItemBinding.inflate(
            LayoutInflater.from(context),
            this,
            false
        )

        binding.elective = elective
        (binding.root as CheckBox).setOnCheckedChangeListener { _, isChecked ->
            binding.elective?.isChecked = isChecked
        }

        addView(binding.root)
    }
}

@BindingAdapter("mobilityModule")
fun RadioButton.setMobilityModule(mobilityModule: Discipline.MobilityModule?) {
    mobilityModule?.let {
        val stringBuilder = SpannableStringBuilder()
            .append(it.name + "\n", RelativeSizeSpan(1.2f), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            .append("Трудоемкость: ${it.intensity} з.е./${it.hours} ч.\n")
            .append("Платформа: ${it.platform}")

        text = stringBuilder
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
        btn.mobilityModule = item
//                btn.root.setOnClickListener { Log.i("TAG", "${it.id} $it") }
        addView(btn.root)
    }
}

@BindingAdapter("elective")
fun CheckBox.setElective(elective: Discipline.Elective?) {
    elective?.let {
        text = it.name
        isChecked = it.isChecked
    }
}

@BindingAdapter("error")
fun EditText.setErrorString(error: String?){
    this.error = error
}

@BindingAdapter("groupInfo")
fun TextView.setGroupInfo(groupInfo: GroupNumberInfo?){
    groupInfo?.run {
        text = context.getString(R.string.groupInfo,course, semester, admissionYear)
    }
}