package com.example.disciplines.ui

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.data.network.model.DisciplinesPair
import com.example.disciplines.data.network.model.Elective
import com.example.disciplines.data.network.model.MobilityModule

@BindingAdapter("discipline")
fun RadioButton.setDiscipline(discipline: Discipline?) {
    discipline?.let {
        text = resources.getString(R.string.discipline, it.name, it.hours)
    }
}

@BindingAdapter("disciplinesPair")
fun RadioGroup.setDisciplinesPair(disciplinesPair: DisciplinesPair?) {
    disciplinesPair?.let {
        when (it.checked) {
            DisciplinesPair.Checked.None -> clearCheck()
            DisciplinesPair.Checked.First -> check(R.id.disciplineRadio1)
            DisciplinesPair.Checked.Second -> check(R.id.disciplineRadio2)
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


@BindingAdapter("elective")
fun CheckBox.setElective(elective: Elective?) {
    elective?.let {
        text = it.name
        isChecked = it.checked
    }
}