package com.example.disciplines.presentation.lists.electives

import androidx.annotation.StringRes
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.presentation.model.Degree
import com.example.disciplines.presentation.model.GroupNumberInfo
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class ElectivesViewModel
@Inject constructor(
    groupInfo: GroupNumberInfo,
    private val disciplinesRepository: DisciplinesRepository
) :
    ViewModel() {
    private val _electivesList = MutableLiveData<List<Discipline.Elective>>()
    val electivesList: LiveData<List<Discipline.Elective>> get() = _electivesList

    private val _navigationEvent = MutableLiveData<List<Discipline.Elective>>()
    val navigationEvent: LiveData<List<Discipline.Elective>> get() = _navigationEvent

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus> get() = _requestStatus

    private val _degreeArgs = MutableLiveData<DegreeArgs?>()
    val degreeArgs: LiveData<DegreeArgs?> get() = _degreeArgs

    init {
        getElectivesList(groupInfo)
    }

    private fun getElectivesList(groupInfo: GroupNumberInfo) {
        viewModelScope.launch {
            _requestStatus.value = RequestStatus.LOADING
            runCatching {
                val list = disciplinesRepository.getElectives(groupInfo.groupNumber)
                _electivesList.value = list.sortedBy { it.name }
                _requestStatus.value = RequestStatus.DONE
                _degreeArgs.value = getDegreeArgs(groupInfo.degree)
            }.onFailure {
                if (it is UnknownHostException) {
                    _electivesList.value = emptyList()
                    _requestStatus.value = RequestStatus.ERROR
                    _degreeArgs.value = null
                } else throw it
            }
        }
    }

    private fun getDegreeArgs(degree: Degree) =
        when (degree) {
            Degree.BACHELOR -> BACHELOR_ARGS
            Degree.MASTER -> MASTER_ARGS
        }

    fun onConfirm() {
        _navigationEvent.value = _electivesList.value?.filter { it.isSelected } ?: emptyList()
    }

    fun navigationFinished() {
        _navigationEvent.value = null
    }


    companion object {
        private const val BACHELOR_NAME = R.string.bachelor_name
        private const val BACHELOR_NEEDED_PEOPLE = 18
        private const val MASTER_NAME = R.string.master_name
        private const val MASTER_NEEDED_PEOPLE = 12
        private val BACHELOR_ARGS = DegreeArgs(BACHELOR_NAME, BACHELOR_NEEDED_PEOPLE)
        private val MASTER_ARGS = DegreeArgs(MASTER_NAME, MASTER_NEEDED_PEOPLE)
    }
}

data class DegreeArgs(
    @StringRes
    val nameRes: Int,
    val neededPeople: Int
)