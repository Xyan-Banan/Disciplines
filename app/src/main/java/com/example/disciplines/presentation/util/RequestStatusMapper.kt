package com.example.disciplines.presentation.util

import android.view.View
import com.example.disciplines.R
import com.example.disciplines.data.source.network.RequestStatus

object RequestStatusMapper {
    fun toConfirmBtnVisibility(status: RequestStatus, list: List<Any>?): Int {
        return when (status) {
            RequestStatus.DONE -> if (list.isNullOrEmpty()) View.GONE else View.VISIBLE
            else -> View.GONE
        }
    }
//
    private const val errorInstruction = "Ошибка при загрузке данных. Проверьте подключение к интернету"

    fun toInstructions(status: RequestStatus, onDone: (() -> CharSequence)): CharSequence {
        return when (status) {
            RequestStatus.DONE -> onDone()
            else -> errorInstruction
        }
    }

    fun toInstructionsVisibility(status: RequestStatus): Int {
        return when (status) {
            RequestStatus.LOADING -> View.GONE
            else -> View.VISIBLE
        }
    }

    fun toStatusImageVisibility(status: RequestStatus): Int{
        return when (status) {
            RequestStatus.DONE -> View.GONE
            else -> View.VISIBLE
        }
    }

    fun toStatusImage(status: RequestStatus): Int{
        return when (status) {
            RequestStatus.ERROR -> R.drawable.ic_connection_error
            else -> R.drawable.loading_animation
        }
    }
}