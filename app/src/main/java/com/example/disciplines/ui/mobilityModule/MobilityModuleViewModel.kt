package com.example.disciplines.ui.mobilityModule

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.disciplines.data.network.model.MobilityModule
import com.example.disciplines.data.network.Network
import com.example.disciplines.data.network.RequestStatus
import kotlinx.coroutines.launch


class MobilityModuleViewModel(groupName: String) : ViewModel() {
    val modulesList = MutableLiveData<List<MobilityModule>>()
    val requestStatus = MutableLiveData<RequestStatus>()

    init {
        getModulesList(groupName)
    }

    private fun getModulesList(groupName: String) {
        viewModelScope.launch {
            val course = groupName[groupName.length - 3].digitToInt()
            requestStatus.value = RequestStatus.LOADING
            try {
                modulesList.value = Network.retrofitService.getMobilityModules(groupName)
//                    .filter { it.intensity >= course }
                requestStatus.value = RequestStatus.DONE
            } catch (e: Exception) {
                modulesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }
}