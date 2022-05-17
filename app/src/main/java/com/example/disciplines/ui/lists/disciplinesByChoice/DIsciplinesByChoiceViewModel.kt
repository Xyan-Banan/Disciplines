package com.example.disciplines.ui.lists.disciplinesByChoice

import android.util.Log
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

@OptIn(ExperimentalTime::class)
class DisciplinesByChoiceViewModel(groupNumber: String) : ViewModel() {
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
        val duration = measureTime { getDisciplines(groupNumber) }
        Log.d(javaClass.simpleName, "Duration: $duration")
    }

    private fun getDisciplines(groupNumber: String) {
        viewModelScope.launch {
            requestStatus.value = RequestStatus.LOADING
            try {
                val list =
                    withContext(Dispatchers.IO) { Network.api.getDisciplinesByChoice(groupNumber) }
//                                        TestValues.generateDisciplinesBundles(15)
                disciplinesList.value = list.asBundlesList()
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