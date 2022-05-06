package com.example.disciplines.ui.mobilityModule

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.network.Network
import com.example.disciplines.data.network.RequestStatus
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.ui.CurrentGroup
import kotlinx.coroutines.launch


class MobilityModuleViewModel : ViewModel() {
    val modulesList = MutableLiveData<List<Discipline.MobilityModule>>()
    private val requestStatus = MutableLiveData<RequestStatus>()
    val confirmBtnVisibility = requestStatus.map {
        when (it) {
            RequestStatus.DONE -> if (modulesList.value.isNullOrEmpty()) View.GONE else View.VISIBLE
            else -> View.GONE
        }
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
        when (it) {
            RequestStatus.LOADING -> View.GONE
            else -> View.VISIBLE
        }
    }
    val statusImageVisibility = requestStatus.map {
        when (it) {
            RequestStatus.DONE -> View.GONE
            else -> View.VISIBLE
        }
    }
    val statusImage = requestStatus.map {
        when (it) {
            RequestStatus.ERROR -> R.drawable.ic_connection_error
            else -> R.drawable.loading_animation
        }
    }

    init {
        val groupName = CurrentGroup.value ?: "353090490300" // TODO: handle null value properly
        getModulesList(groupName)
    }

    private fun getModulesList(groupName: String) {
        viewModelScope.launch {
            val course = groupName[groupName.length - 3].digitToInt()
            requestStatus.value = RequestStatus.LOADING
            try {
                modulesList.value = Network.api.getMobilityModules(groupName)
//                modulesList.value = Network.api.getMobilityModules()
                    .filter { it.intensity >= course }
                requestStatus.value = RequestStatus.DONE
            } catch (e: Exception) {
                println(e.message)
                modulesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }
}