package com.example.disciplines.presentation.lists.disciplinesByChoice

import androidx.lifecycle.*
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
    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus> get() = _requestStatus

    fun getDisciplines(groupNumber: String) {
        viewModelScope.launch {
            _requestStatus.value = RequestStatus.LOADING
            try {
                disciplinesList.value = disciplinesRepository.getDisciplinesByChoice(groupNumber)
                _requestStatus.value = RequestStatus.DONE
            } catch (e: UnknownHostException) {
                println(e.message)
                println(e.javaClass)
                disciplinesList.value = emptyList()
                _requestStatus.value = RequestStatus.ERROR
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