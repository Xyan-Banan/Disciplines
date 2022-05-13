package com.example.disciplines

import java.io.Serializable
import java.util.*

class GroupNumberInfo(gNumber: String) : Serializable {
    val groupNumber: String
    val course: Int
    val semester: Int
    val admissionYear: Int
    val formOfEducation: FormOfEducation

    init {
        groupNumber = gNumber.lowercase().replace("/", "")
        formOfEducation = when (groupNumber.first()) {
            'в' -> FormOfEducation.EVENING
            'з' -> FormOfEducation.DISTANCE
            else -> FormOfEducation.DAILY
        }
        val onlyNumbers = groupNumber.dropWhile { it.isLetter() }
        course = onlyNumbers[onlyNumbers.length - 3].digitToInt()
        val calendar = Calendar.getInstance()
        semester = (course - 1) * 2 + if (calendar[Calendar.MONTH] >= 8) 1 else 2
        admissionYear = calendar.get(Calendar.YEAR) - course
    }

    enum class FormOfEducation {
        DAILY,
        EVENING,
        DISTANCE
    }
}