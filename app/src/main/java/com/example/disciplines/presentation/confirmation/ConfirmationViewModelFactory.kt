package com.example.disciplines.presentation.confirmation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.GroupNumberInfo
import com.example.disciplines.data.models.SelectedDisciplines

class ConfirmationViewModelFactory(
    private val selected: SelectedDisciplines,
    private val groupInfo: GroupNumberInfo,
    private val application: Application
) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ConfirmationViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ConfirmationViewModel(selected, groupInfo, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}