package com.example.disciplines.ui

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.view.get
import androidx.databinding.BindingAdapter
import com.example.disciplines.R
import com.example.disciplines.data.network.model.DisciplineS
import com.example.disciplines.data.network.model.DisciplinesBundle
import com.example.disciplines.data.network.model.Elective
import com.example.disciplines.data.network.model.MobilityModule
import com.example.disciplines.databinding.DisciplineItemBinding
import com.example.disciplines.databinding.MobilityModuleItemBinding

@BindingAdapter("discipline")
fun RadioButton.setDiscipline(discipline: DisciplineS.ByChoice?) {
    discipline?.let {
        text = resources.getString(R.string.discipline, it.name, it.hours)
    }
}

@BindingAdapter("disciplinesBundle")
fun RadioGroup.setDisciplinesPair(disciplinesBundle: DisciplinesBundle?) {
    disciplinesBundle?.let {
        removeAllViews()
        it.list.forEach {
            val btn = DisciplineItemBinding.inflate(
                LayoutInflater.from(context),
                this,
                false
            )

            btn.discipline = it

            addView(btn.root)
        }

        when (it.checkedIndex) {
            -1 -> clearCheck()
            else -> check(get(it.checkedIndex).id)
        }
    }
}

@BindingAdapter("mobilityModule")
fun RadioButton.setMobilityModule(mobilityModule: MobilityModule?) {
    mobilityModule?.let {
        val stringBuilder = SpannableStringBuilder()
            .append(it.name + "\n", RelativeSizeSpan(1.2f), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            .append("Трудоемкость: ${it.intensity} з.е./${it.hours} ч.\n")
            .append("Платформа: ${it.platform}")

        text = stringBuilder
    }
}

@BindingAdapter("mobilityModules")
fun RadioGroup.setMobilityModules(list: List<MobilityModule>?) {
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
fun CheckBox.setElective(elective: Elective?) {
    elective?.let {
        text = it.name
        isChecked = it.checked
    }
}