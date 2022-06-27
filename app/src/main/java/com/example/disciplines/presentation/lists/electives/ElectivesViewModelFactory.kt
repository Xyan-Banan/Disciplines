package com.example.disciplines.presentation.lists.electives

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.GroupNumberInfo
import com.example.disciplines.domain.repositories.DisciplinesRepository

class ElectivesViewModelFactory(
    private val application: Application,
    private val disciplinesRepository: DisciplinesRepository,
    private val groupInfo: GroupNumberInfo
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectivesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ElectivesViewModel(application, disciplinesRepository, groupInfo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
