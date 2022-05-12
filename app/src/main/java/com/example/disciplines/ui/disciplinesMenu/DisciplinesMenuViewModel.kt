package com.example.disciplines.ui.disciplinesMenu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DisciplinesMenuViewModel : ViewModel() {
    private val _groupNumber = MutableLiveData<String>()
    val groupNumber: LiveData<String>
        get() = _groupNumber

    val error = MutableLiveData<String?>(null)
    val isValidGroupNumber = MutableLiveData(false)

    private fun isValidGroupNumber(gNumber: String): Boolean {
        return Regex("""^[вз]?\d{7}/?\d{5}${'$'}""").matches(gNumber)
    }

    private fun isDailyEducation(gNumber: String): Boolean {
        return Regex("""^\d{7}/?\d{5}${'$'}""").matches(gNumber)
    }

    fun submitGroupNumber(gNumber: String) {
        when {
            gNumber.isBlank() -> {
                error.value = null
                isValidGroupNumber.value = false
            }
            isValidGroupNumber(gNumber) -> {
                if (!isDailyEducation(gNumber)) {
                    error.value = "На данный момент нет данных для вечерних и заочных групп"
                    isValidGroupNumber.value = false
                } else {
                    error.value = null
                    isValidGroupNumber.value = true
                    _groupNumber.value = gNumber
                }
            }
            else -> {
                error.value = "Неверно введен номер группы"
                isValidGroupNumber.value = false
            }
        }
    }
}