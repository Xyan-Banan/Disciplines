package com.example.disciplines.presentation.lists.disciplinesByChoice

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.models.DisciplinesBundle
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.presentation.util.RequestStatusMapper
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class DisciplinesByChoiceViewModel
@Inject constructor(
    private val disciplinesRepository: DisciplinesRepository
) : ViewModel() {
    val disciplinesList = MutableLiveData<List<DisciplinesBundle>>()
    private val requestStatus = MutableLiveData<RequestStatus>()

    val confirmBtnVisibility = requestStatus.map {
        RequestStatusMapper.toConfirmBtnVisibility(it, disciplinesList.value)
    }
    val instructions = requestStatus.map {
        when (it) {
            RequestStatus.DONE ->
                if (disciplinesList.value.isNullOrEmpty()) R.string.instructions_disciplinesByChoice_empty
                else R.string.instructions_disciplinesByChoice
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

    fun getDisciplines(groupNumber: String) {
        viewModelScope.launch {
            requestStatus.value = RequestStatus.LOADING
            try {
                disciplinesList.value = disciplinesRepository.getDisciplinesByChoice(groupNumber)
                requestStatus.value = RequestStatus.DONE
            } catch (e: UnknownHostException) {
                println(e.message)
                println(e.javaClass)
                disciplinesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }

    val isCanNavigate: Boolean
        get() {
            disciplinesList.value?.let {
                return checked == it.size
            }

            return false
        }
    val checked: Int
        get() = disciplinesList.value?.count { it.checkedIndex >= 0 } ?: 0
}