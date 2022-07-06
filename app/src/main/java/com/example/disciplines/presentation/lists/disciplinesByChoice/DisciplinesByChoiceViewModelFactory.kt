package com.example.disciplines.presentation.lists.disciplinesByChoice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.domain.repositories.DisciplinesRepository

class DisciplinesByChoiceViewModelFactory(
    private val disciplinesRepository: DisciplinesRepository,
    private val groupNumber: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DisciplinesByChoiceViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DisciplinesByChoiceViewModel(
                disciplinesRepository,
//                groupNumber
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}