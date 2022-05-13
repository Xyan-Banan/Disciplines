package com.example.disciplines.ui.electives

import android.app.Application
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.network.Network
import com.example.disciplines.data.network.RequestStatus
import com.example.disciplines.data.network.TestValues
import com.example.disciplines.data.network.model.Discipline
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.UnknownHostException

class ElectivesViewModel(private val app: Application, private val groupNumber: String) :
    AndroidViewModel(app) {
    val electivesList = MutableLiveData<List<Discipline.Elective>>()
    val requestStatus = MutableLiveData<RequestStatus>()

    val confirmBtnVisibility = requestStatus.map {
        when (it) {
            RequestStatus.DONE -> if (electivesList.value.isNullOrEmpty()) View.GONE else View.VISIBLE
            else -> View.GONE
        }
    }
    val instructions = requestStatus.map {
        when (it) {
            RequestStatus.DONE -> getInstructions(groupNumber)
            else -> app.getString(R.string.instructions_error_loading)
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
        getElectivesList(groupNumber)
    }

    private fun getElectivesList(groupNumber: String) {
        viewModelScope.launch {
            requestStatus.value = RequestStatus.LOADING
            try {
                val list = withContext(Dispatchers.IO) { Network.api.getElectives(groupNumber) }
                //TestValues.generateElectives(5)
                electivesList.value = list
                requestStatus.value = RequestStatus.DONE
            } catch (e: UnknownHostException) {
                electivesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }

    private fun getInstructions(groupNumber: String): CharSequence {
        if (electivesList.value.isNullOrEmpty())
            return app.getString(R.string.instructions_electives_empty)
        else {
            val isBachelor = groupNumber[2].digitToInt() == 3
            val (studentType, neededPeople) = if (isBachelor) "бакалавриата" to 18 else "магистратуры" to 12
            val spannable = SpannableString(
                app.getString(
                    R.string.instructions_electives,
                    studentType,
                    neededPeople
                )
            )
            spannable.setSpan(
                RelativeSizeSpan(0.8f),
                spannable.indexOf('\n') + 1,
                spannable.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            return spannable
        }
    }
}