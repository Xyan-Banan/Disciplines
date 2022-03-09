package com.example.disciplines.ui

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.example.disciplines.R
import com.example.disciplines.data.database.Discipline
import com.example.disciplines.data.database.DisciplinesPair
import com.example.disciplines.data.database.MobilityModule

@BindingAdapter("discipline")
fun RadioButton.setDiscipline(discipline: Discipline?) {
    discipline?.apply {
        text = resources.getString(R.string.discipline, name, hours)
    }
}

@BindingAdapter("mobilityModule")
fun RadioButton.setMobilityModule(mobilityModule: MobilityModule?){
    mobilityModule?.apply {
        val disciplinesList = disciplines.joinToString("\n") { "-${it.name} (${it.hours} часов)" }
        text = resources.getString(R.string.mobilityModuleCardText, name, intensity, disciplinesList)
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