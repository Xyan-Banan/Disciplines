package com.example.disciplines.ui

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.databinding.BindingAdapter
import com.example.disciplines.R
import com.example.disciplines.data.database.Discipline
import com.example.disciplines.data.database.DisciplinesPair

@BindingAdapter("discipline")
fun RadioButton.setDiscipline(discipline: Discipline?) {
    discipline?.apply {
        text = resources.getString(R.string.discipline, name, hours)
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