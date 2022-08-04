package com.example.disciplines.presentation.lists.mobilityModule

import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.presentation.model.GroupNumberInfo
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject


class MobilityModuleViewModel
@Inject constructor(
    groupInfo: GroupNumberInfo,
    private val disciplinesRepository: DisciplinesRepository
) : ViewModel() {
    private val _modulesList = MutableLiveData<List<Discipline.MobilityModule>>()
    val modulesList: LiveData<List<Discipline.MobilityModule>> get() = _modulesList

    private val _requestStatus = MutableLiveData<RequestStatus>()
    val requestStatus: LiveData<RequestStatus> get() = _requestStatus

    private val _navigationEvent = MutableLiveData<NavigationEvent?>()
    val navigationEvent: LiveData<NavigationEvent?> get() = _navigationEvent

    init {
        getModulesList(groupInfo.groupNumber)
    }

    private fun getModulesList(groupNumber: String) {
        viewModelScope.launch {
            _requestStatus.value = RequestStatus.LOADING
            runCatching {
                val list = disciplinesRepository.getMobilityModules(groupNumber)
                _modulesList.value = list
                _requestStatus.value = RequestStatus.DONE
            }.onFailure { e ->
                if (e is UnknownHostException) {
                    println(e.message)
                    _modulesList.value = emptyList()
                    _requestStatus.value = RequestStatus.ERROR
                } else throw e
            }
        }
    }

    fun onConfirm(radioGroup: RadioGroup) {
        val canNavigate = (radioGroup.checkedRadioButtonId >= 0)

        _navigationEvent.value = if (canNavigate) {
            val checkedRadioButton =
                radioGroup.findViewById<RadioButton>(radioGroup.checkedRadioButtonId)
            val checkedIndex = radioGroup.indexOfChild(checkedRadioButton)
            NavigationEvent.Can(_modulesList.value!![checkedIndex])
        } else
            NavigationEvent.Not
    }

    fun navigationFinished() {
        _navigationEvent.value = null
    }
}