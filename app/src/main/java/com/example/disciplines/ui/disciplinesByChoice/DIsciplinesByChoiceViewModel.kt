package com.example.disciplines.ui.disciplinesByChoice

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.network.Network
import com.example.disciplines.data.network.RequestStatus
import com.example.disciplines.data.network.model.DisciplinesBundle
import com.example.disciplines.data.network.model.asBundlesList
import com.example.disciplines.ui.CurrentGroup
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class DisciplinesByChoiceViewModel : ViewModel() {
    val disciplinesList = MutableLiveData<List<DisciplinesBundle>>()
    private val requestStatus = MutableLiveData<RequestStatus>()

    val confirmBtnVisibility = requestStatus.map {
        when (it) {
            RequestStatus.DONE -> if (disciplinesList.value.isNullOrEmpty()) View.GONE else View.VISIBLE
            else -> View.GONE
        }
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
        val groupName = CurrentGroup.value ?: "353090490000" // TODO: handle null value properly
        getDisciplines(groupName)
    }

    private fun getDisciplines(groupName: String) {
        viewModelScope.launch {
            requestStatus.value = RequestStatus.LOADING
            try {
                disciplinesList.value = //TestValues.generateDisciplinesBundles(15)
                    Network.api.getDisciplinesByChoice(groupName).asBundlesList()
                requestStatus.value = RequestStatus.DONE
            } catch (e: UnknownHostException) {
                println(e.message)
                println(e.javaClass)
                disciplinesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }
}