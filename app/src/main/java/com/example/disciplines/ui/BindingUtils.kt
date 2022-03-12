package com.example.disciplines.ui

import android.os.Build
import android.text.*
import android.text.style.BulletSpan
import android.text.style.RelativeSizeSpan
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.BindingAdapter
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.data.network.model.DisciplinesPair
import com.example.disciplines.data.network.model.MobilityModule

@BindingAdapter("discipline")
fun RadioButton.setDiscipline(discipline: Discipline?) {
    discipline?.apply {
        text = resources.getString(R.string.discipline, name, hours)
    }
}

@BindingAdapter("mobilityModule")
fun RadioButton.setMobilityModule(mobilityModule: MobilityModule?) {
    mobilityModule?.apply {

        val stringBuilder = SpannableStringBuilder()
            .append(name + "\n", RelativeSizeSpan(1.2f), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            .append("Трудоемкость: $intensity з.е./$hours ч.\n")
            .append("Платформа: $platform")

        text = stringBuilder
    }
}

@BindingAdapter("disciplinesPair")
fun RadioGroup.setDisciplinesPair(disciplinesPair: DisciplinesPair?) {
    disciplinesPair ?: return

    when (disciplinesPair.checked) {
        DisciplinesPair.Checked.None -> clearCheck()
        DisciplinesPair.Checked.First -> check(R.id.disciplineRadio1)
        DisciplinesPair.Checked.Second -> check(R.id.disciplineRadio2)
    }
}