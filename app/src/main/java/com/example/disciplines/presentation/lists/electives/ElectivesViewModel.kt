package com.example.disciplines.presentation.lists.electives

import android.app.Application
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.disciplines.Degree
import com.example.disciplines.GroupNumberInfo
import com.example.disciplines.R
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.data.models.Discipline
import kotlinx.coroutines.launch
import java.net.UnknownHostException

class ElectivesViewModel(
    private val app: Application,
    private val disciplinesRepository: DisciplinesRepository,
    groupInfo: GroupNumberInfo
) :
    AndroidViewModel(app) {
    val electivesList = MutableLiveData<List<Discipline.Elective>>()
    private val requestStatus = MutableLiveData<RequestStatus>()

    val confirmBtnVisibility = requestStatus.map {
        when (it) {
            RequestStatus.DONE -> if (electivesList.value.isNullOrEmpty()) View.GONE else View.VISIBLE
            else -> View.GONE
        }
    }
    val instructions = requestStatus.map {
        when (it) {
            RequestStatus.DONE -> getInstructions(groupInfo)
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
        getElectivesList(groupInfo)
    }

    private fun getElectivesList(groupInfo: GroupNumberInfo) {
        viewModelScope.launch {
            requestStatus.value = RequestStatus.LOADING
            try {
                val list = disciplinesRepository.getElectives(groupInfo.groupNumber)
                electivesList.value = list.sortedBy { it.name }
                requestStatus.value = RequestStatus.DONE
            } catch (e: UnknownHostException) {
                electivesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }

    private fun getInstructions(groupInfo: GroupNumberInfo): CharSequence {
        if (electivesList.value.isNullOrEmpty())
            return app.getString(R.string.instructions_electives_empty)
        else {
            val (studentType, neededPeople) = when (groupInfo.degree) {
                Degree.BACHELOR -> BACHELOR_NAME to BACHELOR_NEEDED_PEOPLE
                Degree.MASTER -> MASTER_NAME to MASTER_NEEDED_PEOPLE
            }
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

    companion object {
        private const val BACHELOR_NAME = "бакалавриата"
        private const val BACHELOR_NEEDED_PEOPLE = 18
        private const val MASTER_NAME = "магистратуры"
        private const val MASTER_NEEDED_PEOPLE = 12
    }
}