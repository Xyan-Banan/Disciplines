package com.example.disciplines.ui.confirmation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.data.network.model.Discipline

class ConfirmationViewModelFactory(
    private val selected: Array<Discipline>,
    private val from: Int,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfirmationViewModel(selected, from, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}