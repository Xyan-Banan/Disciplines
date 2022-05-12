package com.example.disciplines.ui.confirmation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.data.network.model.SelectedDisciplines

class ConfirmationViewModelFactory(
    private val selected: SelectedDisciplines,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfirmationViewModel(selected, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}