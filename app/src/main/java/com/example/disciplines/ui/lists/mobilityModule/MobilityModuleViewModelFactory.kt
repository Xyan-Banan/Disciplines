package com.example.disciplines.ui.lists.mobilityModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MobilityModuleViewModelFactory(private val groupNumber: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MobilityModuleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MobilityModuleViewModel(groupNumber) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}