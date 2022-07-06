package com.example.disciplines.presentation.lists.electives

import android.content.res.Resources
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import androidx.lifecycle.*
import com.example.disciplines.Degree
import com.example.disciplines.GroupNumberInfo
import com.example.disciplines.R
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.presentation.lists.RequestStatusMapper
import kotlinx.coroutines.launch
import java.net.UnknownHostException
import javax.inject.Inject

class ElectivesViewModel
@Inject constructor(
    private val disciplinesRepository: DisciplinesRepository
) :
    ViewModel() {
    val electivesList = MutableLiveData<List<Discipline.Elective>>()
    val requestStatus = MutableLiveData<RequestStatus>()

    val confirmBtnVisibility = requestStatus.map {
        RequestStatusMapper.toConfirmBtnVisibility(it, electivesList.value)
    }

    val instructionsVisibility = requestStatus.map {
        RequestStatusMapper.toInstructionsVisibility(it)
    }
    val statusImageVisibility = requestStatus.map {
        RequestStatusMapper.toStatusImageVisibility(it)
    }
    val statusImage = requestStatus.map {
        RequestStatusMapper.toStatusImage(it)
    }

    fun getElectivesList(groupNumber: String) {
        viewModelScope.launch {
            requestStatus.value = RequestStatus.LOADING
            try {
                val list = disciplinesRepository.getElectives(groupNumber)
                electivesList.value = list.sortedBy { it.name }
                requestStatus.value = RequestStatus.DONE
            } catch (e: UnknownHostException) {
                electivesList.value = emptyList()
                requestStatus.value = RequestStatus.ERROR
            }
        }
    }

    fun getInstructions(resources: Resources, groupInfo: GroupNumberInfo): CharSequence {
        return when (requestStatus.value) {
            RequestStatus.DONE -> getInstructionsOnDone(resources, groupInfo)
            else -> resources.getString(R.string.instructions_error_loading)
        }
    }

    private fun getInstructionsOnDone(resources: Resources, groupInfo: GroupNumberInfo): CharSequence {
        return if (electivesList.value.isNullOrEmpty())
            resources.getString(R.string.instructions_electives_empty)
        else {
            val (studentType, neededPeople) = getDegreeArgs(groupInfo)
            val spannable = SpannableString(
                resources.getString(
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
            spannable
        }
    }

    private fun getDegreeArgs(groupInfo: GroupNumberInfo) =
        when (groupInfo.degree) {
            Degree.BACHELOR -> BACHELOR_PAIR
            Degree.MASTER -> MASTER_PAIR
        }

    companion object {
        private const val BACHELOR_NAME = "бакалавриата"
        private const val BACHELOR_NEEDED_PEOPLE = 18
        private const val MASTER_NAME = "магистратуры"
        private const val MASTER_NEEDED_PEOPLE = 12
        private val BACHELOR_PAIR = BACHELOR_NAME to BACHELOR_NEEDED_PEOPLE
        private val MASTER_PAIR = MASTER_NAME to MASTER_NEEDED_PEOPLE
    }
}