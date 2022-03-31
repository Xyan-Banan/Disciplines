package com.example.disciplines.ui.electives

import android.app.Application
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.network.RequestStatus
import com.example.disciplines.data.network.TestValues
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.ui.CurrentGroup
import com.example.disciplines.ui.listUtils.Header
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class ElectivesViewModel(private val app: Application) : AndroidViewModel(app) {
    val electivesList = MutableLiveData<List<Discipline.Elective>>()
    val requestStatus = MutableLiveData<RequestStatus>()
    val header = requestStatus.map {
        val title = app.getString(R.string.electives)
        val instructions = when (it) {
            RequestStatus.DONE -> getInstructions()
            else -> app.getString(R.string.instructions_error_loading)
        }
        Header(title, instructions)
    }

    init {
        val groupName = CurrentGroup.value ?: "353090490010"
        getElectivesList(groupName)
    }

    private fun getElectivesList(groupName: String) {
        viewModelScope.launch {
            requestStatus.value = RequestStatus.LOADING
            try {
                electivesList.value = TestValues.generateElectives(15)
                //Network.api.getElectives(groupName)
                requestStatus.value = RequestStatus.DONE
            } catch (e: UnknownHostException) {
                electivesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }

    private fun getInstructions(): CharSequence {
        if (electivesList.value.isNullOrEmpty())
            return app.getString(R.string.instructions_electives_empty)
        else {
            val groupName = CurrentGroup.value ?: "353090490010"
            val isBachelor = groupName[2].digitToInt() == 3
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