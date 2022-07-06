package com.example.disciplines.presentation

import java.util.*

object GroupNumberInfoFactory {
    private const val BACHELOR_NUMBER = '3'
    private const val EDUCATION_LETTER_EVENING = 'в'
    private const val EDUCATION_LETTER_DISTANCE = 'з'
    private const val DIVIDER = "/"

    fun fromString(gNumber: String): GroupNumberInfo {
        val groupNumber = gNumber.lowercase().replace(DIVIDER, "")
        val formOfEducation = when (groupNumber.first()) {
            EDUCATION_LETTER_EVENING -> FormOfEducation.EVENING
            EDUCATION_LETTER_DISTANCE -> FormOfEducation.DISTANCE
            else -> FormOfEducation.DAILY
        }
        val onlyNumbers = groupNumber.dropWhile { it.isLetter() }
        val degree = if (onlyNumbers[2] == BACHELOR_NUMBER) Degree.BACHELOR else Degree.MASTER
        val course = onlyNumbers[onlyNumbers.length - 3].digitToInt()
        val calendar = Calendar.getInstance()
        val semester = (course - 1) * 2 + if (calendar[Calendar.MONTH] >= 8) 1 else 2
        val admissionYear = calendar.get(Calendar.YEAR) - course

        return GroupNumberInfo(
            groupNumber,
            course,
            semester,
            admissionYear,
            formOfEducation,
            degree
        )
    }
}