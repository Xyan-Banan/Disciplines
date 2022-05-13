package com.example.disciplines.ui.electives

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ElectivesViewModelFactory(
    private val application: Application,
    private val groupNumber: String
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectivesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ElectivesViewModel(application, groupNumber) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
