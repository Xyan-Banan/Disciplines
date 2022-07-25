package com.example.disciplines.presentation.confirmation

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.presentation.model.GroupNumberInfo
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

class ConfirmationViewModelFactory @AssistedInject constructor(
    @Assisted(ASSISTED_KEY_SELECTED)
    private val selected: SelectedDisciplines,
//    @Assisted(ASSISTED_KEY_GROUP_INFO)
    private val groupInfo: GroupNumberInfo,
    private val application: Application
) :
    ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(ConfirmationViewModel::class.java)) { "Unknown ViewModel class" }
        return ConfirmationViewModel(selected, groupInfo, application) as T
    }

    @AssistedFactory
    interface Factory {
        fun create(
            @Assisted(ASSISTED_KEY_SELECTED)
            selected: SelectedDisciplines,
//            @Assisted(ASSISTED_KEY_GROUP_INFO)
//            groupInfo: GroupNumberInfo
        ): ConfirmationViewModelFactory
    }

    private companion object {
        private const val ASSISTED_KEY_SELECTED = "selected"
        private const val ASSISTED_KEY_GROUP_INFO = "groupInfo"
    }
}