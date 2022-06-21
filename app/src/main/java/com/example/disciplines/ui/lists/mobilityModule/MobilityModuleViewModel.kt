package com.example.disciplines.ui.lists.mobilityModule

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.DisciplinesRepository
import com.example.disciplines.data.source.network.DisciplinesRemoteDataSource
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.data.model.Discipline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MobilityModuleViewModel(
    private val disciplinesRepository: DisciplinesRepository,
    groupNumber: String
) : ViewModel() {
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
        getModulesList(groupNumber)
    }

    private fun getModulesList(groupNumber: String) {
        viewModelScope.launch {
            val course = groupNumber[groupNumber.length - 3].digitToInt()
            requestStatus.value = RequestStatus.LOADING
            try {
                val list = disciplinesRepository.getMobilityModules(groupNumber)
                modulesList.value = list.filter { it.intensity >= course }
                requestStatus.value = RequestStatus.DONE
            } catch (e: Exception) {
                println(e.message)
                modulesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }
}