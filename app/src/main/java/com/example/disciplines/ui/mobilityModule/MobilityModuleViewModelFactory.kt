package com.example.disciplines.ui.mobilityModule

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MobilityModuleViewModelFactory(private val groupName: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MobilityModuleViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MobilityModuleViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}