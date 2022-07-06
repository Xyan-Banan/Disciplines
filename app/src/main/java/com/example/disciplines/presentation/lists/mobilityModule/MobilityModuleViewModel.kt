package com.example.disciplines.presentation.lists.mobilityModule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.presentation.lists.RequestStatusMapper
import kotlinx.coroutines.launch
import javax.inject.Inject


class MobilityModuleViewModel
@Inject constructor(
    private val disciplinesRepository: DisciplinesRepository
) : ViewModel() {
    val modulesList = MutableLiveData<List<Discipline.MobilityModule>>()
    private val requestStatus = MutableLiveData<RequestStatus>()
    val confirmBtnVisibility = requestStatus.map {
        RequestStatusMapper.toConfirmBtnVisibility(it, modulesList.value)
    }
    val instructions = requestStatus.map {
        when (it) {
            RequestStatus.DONE ->
                if (modulesList.value.isNullOrEmpty()) R.string.instructions_mobilityModule_empty
                else R.string.instructions_mobilityModule
            else -> R.string.instructions_error_loading
        }
    }
    val instructionsVisibility = requestStatus.map {
        RequestStatusMapper.toInstructionsVisibility(it)
    }
    val statusImageVisibility = requestStatus.map {
        RequestStatusMapper.toStatusImageVisibility(it)
    }
    val statusImage = requestStatus.map {
        RequestStatusMapper.toStatusImage(it)
    }

    fun getModulesList(groupNumber: String) {
        viewModelScope.launch {
            requestStatus.value = RequestStatus.LOADING
            try {
                val list = disciplinesRepository.getMobilityModules(groupNumber)
                modulesList.value = list
                requestStatus.value = RequestStatus.DONE
            } catch (e: Exception) {
                println(e.message)
                modulesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }
}