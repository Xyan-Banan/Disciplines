package com.example.disciplines.ui

import android.widget.RadioButton
import androidx.databinding.BindingAdapter
import com.example.disciplines.R
import com.example.disciplines.data.database.Discipline

@BindingAdapter("discipline")
fun RadioButton.setDiscipline(discipline: Discipline){
    text = resources.getString(R.string.discipline, discipline.name, discipline.hours)
}