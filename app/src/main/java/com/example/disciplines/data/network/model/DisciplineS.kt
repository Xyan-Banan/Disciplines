package com.example.disciplines.data.network.model

import java.util.*

sealed class DisciplineS {
    data class ByChoice(val id: UUID = UUID.randomUUID(), val name: String, val intensity: Int) :
        DisciplineS()

    data class MobilityModule(
        val name: String,
        val platform: String,
        val intensity: Int,
        val hours: Int,
        val link: String = ""
    ) : DisciplineS()

    data class Elective(val name: String, var isChecked: Boolean = false) : DisciplineS()
}

data class DisciplinesBundle(val list: List<DisciplineS.ByChoice>, var checkedIndex: Int = -1)

fun List<List<DisciplineS.ByChoice>>.asBundlesList() = map { DisciplinesBundle(it) }