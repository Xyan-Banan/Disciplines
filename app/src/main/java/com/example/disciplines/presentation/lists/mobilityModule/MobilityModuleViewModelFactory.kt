package com.example.disciplines.presentation.lists.mobilityModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.domain.repositories.DisciplinesRepository

class MobilityModuleViewModelFactory(
    private val disciplinesRepository: DisciplinesRepository,
    private val groupNumber: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MobilityModuleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MobilityModuleViewModel(
                disciplinesRepository,
//                groupNumber
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}