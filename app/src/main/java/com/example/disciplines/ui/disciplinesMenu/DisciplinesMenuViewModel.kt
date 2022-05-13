package com.example.disciplines.ui.disciplinesMenu

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.example.disciplines.GroupNumberInfo
import java.util.*

class DisciplinesMenuViewModel : ViewModel() {
    val groupNumberInfo = MutableLiveData<GroupNumberInfo?>()
    val error = MutableLiveData<String?>(null)
    val isValidGroupNumber = MutableLiveData(false)

    val isGroupInfoVisible = groupNumberInfo.map {
        if (it == null) View.GONE else View.VISIBLE
    }

    private fun isValidGroupNumber(gNumber: String): Boolean {
        return Regex("""^[вВзЗ]?\d{7}/?\d{5}$""").matches(gNumber)
    }

    private fun isDailyEducation(gNumber: String): Boolean {
        return Regex("""^\d{7}/?\d{5}$""").matches(gNumber)
    }

    fun submitGroupNumber(gNumber: String) {
        isValidGroupNumber.value = false
        groupNumberInfo.value = null
        error.value = null

        //пустые - не валидные, но и не ошибка
        if (gNumber.isBlank()) {
            return
        }

        //общее соответствие паттерну группы
        if (!isValidGroupNumber(gNumber)) {
            error.value = "Неверно введен номер группы"
            return
        }

        //соответствие паттерну дневных групп
        if (!isDailyEducation(gNumber)) {
            error.value = "На данный момент нет данных для вечерних и заочных групп"
            return
        }

        //соответствие указанного года поступления и текущего курса
        if (!isValidCourseAndYear(gNumber)) {
            error.value = "Проверьте указанный курс и год поступления"
            return
        }

        isValidGroupNumber.value = true
        groupNumberInfo.value = GroupNumberInfo(gNumber)
    }

    /**
     * Проверяет соответсвите введенных года и курса.
     *
     * Пример: курс - 4, год поступления - 2019, текущий год - 2022.
     *
     * Вывод: 2022 - 2019 != 4 -> false
     */
    private fun isValidCourseAndYear(gNumber: String): Boolean {
        val currentYear = Calendar.getInstance().get(Calendar.YEAR)
        val year = gNumber[gNumber.length - 5].digitToInt()
        val course = gNumber[gNumber.length - 3].digitToInt()
        return (currentYear - course) % 10 == year
    }
}
