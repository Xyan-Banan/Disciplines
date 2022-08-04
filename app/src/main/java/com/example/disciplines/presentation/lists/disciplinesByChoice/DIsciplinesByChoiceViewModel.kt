package com.example.disciplines.presentation.lists.disciplinesByChoice

import android.util.Log
import androidx.lifecycle.*
import com.example.disciplines.data.models.DisciplinesBundle
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.presentation.model.GroupNumberInfo
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class DisciplinesByChoiceViewModel
@Inject constructor(
    groupInfo: GroupNumberInfo,
    private val disciplinesRepository: DisciplinesRepository
) : ViewModel() {
    private val _disciplinesList = MutableLiveData<List<DisciplinesBundle>>()
    val disciplinesList: LiveData<List<DisciplinesBundle>> get() = _disciplinesList

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus> get() = _requestStatus

    private val _navigationEvent = MutableLiveData<NavigationEvent>()
    val navigationEvent: LiveData<NavigationEvent> get() = _navigationEvent

    init {
        getDisciplines(groupInfo.groupNumber)
    }

    private fun getDisciplines(groupNumber: String) {
        viewModelScope.launch {
            _requestStatus.value = RequestStatus.LOADING
            runCatching {
                _disciplinesList.value = disciplinesRepository.getDisciplinesByChoice(groupNumber)
                _requestStatus.value = RequestStatus.DONE
            }.onFailure {
                if (it is UnknownHostException) {
                    Log.e(TAG, "getDisciplines: ", it)
                    _disciplinesList.value = emptyList()
                    _requestStatus.value = RequestStatus.ERROR
                } else throw it
            }
        }
    }

    fun onConfirm() {
        val checked = disciplinesList.value?.count { it.checkedIndex >= 0 }
        val total = disciplinesList.value?.size ?: 0
        _navigationEvent.value = when (checked == total) {
            true -> NavigationEvent.Can(disciplinesList.value!!)
            false -> NavigationEvent.Not(checked ?: 0, total)
        }
    }

    fun navigationFinished() {
        _navigationEvent.value = null
    }

    companion object {
        private const val TAG = "DisciplinesByChoiceView"
    }
}