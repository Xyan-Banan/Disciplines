package com.example.disciplines.ui.electives

import android.app.Application
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ElectivesViewModelFactory(
    private val application: Application,
    private val listener: View.OnClickListener
) :
    ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ElectivesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ElectivesViewModel(application, listener) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
