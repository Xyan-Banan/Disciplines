package com.example.disciplines.presentation.disciplinesMenu

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import androidx.navigation.NavDirections
import com.example.disciplines.R
import com.example.disciplines.presentation.model.GroupNumberInfo
import com.example.disciplines.presentation.util.GroupNumberInfoFactory
import java.util.*

class DisciplinesMenuViewModel : ViewModel() {
    private val _groupNumberInfo = MutableLiveData<GroupNumberInfo?>()
    val groupNumberInfo: LiveData<GroupNumberInfo?>
        get() = _groupNumberInfo

    private val _error = MutableLiveData<Int?>(null)
    val error: LiveData<Int?>
        get() = _error


    private val _isValidGroupNumber = MutableLiveData(false)
    val isValidGroupNumber: LiveData<Boolean>
        get() = _isValidGroupNumber

    val isGroupInfoVisible = _groupNumberInfo.map {
        if (it == null) View.GONE else View.VISIBLE
    }

    private fun isValidGroupNumber(gNumber: String): Boolean {
        return Regex(VALID_GROUP_NUMBER_PATTERN).matches(gNumber)
    }

    private fun isDailyEducation(gNumber: String): Boolean {
        return Regex(VALID_DAILY_GROUP_NUMBER_PATTERN).matches(gNumber)
    }

    fun submitGroupNumber(gNumber: String) {
        _isValidGroupNumber.value = false
        _groupNumberInfo.value = null
        _error.value = null

        //пустые - не валидные, но и не ошибка
        if (gNumber.isBlank()) {
            return
        }

        //общее соответствие паттерну группы
        if (!isValidGroupNumber(gNumber)) {
            _error.value = R.string.error_invalid_group_number
            return
        }

        //соответствие паттерну дневных групп
        if (!isDailyEducation(gNumber)) {
            _error.value = R.string.error_no_data_for_evening
            return
        }

        //соответствие указанного года поступления и текущего курса
        if (!isValidCourseAndYear(gNumber)) {
            _error.value = R.string.error_invalid_course_and_year
            return
        }

        _isValidGroupNumber.value = true
        _groupNumberInfo.value = GroupNumberInfoFactory.fromString(gNumber)
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

    fun dropGroupInfo() {
        _isValidGroupNumber.value = false
        _error.value = null
        _groupNumberInfo.value = null
    }

    private val _navigationEvent = MutableLiveData<NavigationHelper?>(null)
    val navigationEvent: LiveData<NavigationHelper?> get() = _navigationEvent

    fun navigate(directions: NavDirections) {
        _navigationEvent.value = NavigationHelper(directions, _groupNumberInfo.value!!)
    }

    fun navigated() {
        _navigationEvent.value = null
    }

    companion object {
        private const val VALID_GROUP_NUMBER_PATTERN = """^[вВзЗ]?\d{7}/?\d{5}$"""
        private const val VALID_DAILY_GROUP_NUMBER_PATTERN = """^\d{7}/?\d{5}$"""
    }
}
