package com.example.disciplines.presentation.lists.mobilityModule

import androidx.lifecycle.*
import com.example.disciplines.R
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.presentation.util.RequestStatusMapper
import kotlinx.coroutines.launch
import javax.inject.Inject


class MobilityModuleViewModel
@Inject constructor(
    private val disciplinesRepository: DisciplinesRepository
) : ViewModel() {
    val modulesList = MutableLiveData<List<Discipline.MobilityModule>>()
    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus> get() = _requestStatus

    fun getModulesList(groupNumber: String) {
        viewModelScope.launch {
            _requestStatus.value = RequestStatus.LOADING
            try {
                val list = disciplinesRepository.getMobilityModules(groupNumber)
                modulesList.value = list
                _requestStatus.value = RequestStatus.DONE
            } catch (e: Exception) {
                println(e.message)
                modulesList.value = emptyList()
                _requestStatus.value = RequestStatus.ERROR
            }
        }
    }
}