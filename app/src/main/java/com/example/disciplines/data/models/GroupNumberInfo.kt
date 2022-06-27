package com.example.disciplines

import java.io.Serializable

data class GroupNumberInfo(
    val groupNumber: String,
    val course: Int,
    val semester: Int,
    val admissionYear: Int,
    val formOfEducation: FormOfEducation,
    val degree: Degree
) : Serializable

enum class FormOfEducation {
    DAILY,
    EVENING,
    DISTANCE
}

enum class Degree {
    BACHELOR,
    MASTER
}