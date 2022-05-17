package com.example.disciplines.ui.lists.disciplinesByChoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DisciplinesByChoiceViewModelFactory(private val groupNumber: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DisciplinesByChoiceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DisciplinesByChoiceViewModel(groupNumber) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}