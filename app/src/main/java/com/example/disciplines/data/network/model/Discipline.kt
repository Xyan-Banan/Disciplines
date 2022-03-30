package com.example.disciplines.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


sealed class Discipline(val dName: String) : Parcelable {
    @Parcelize
    data class ByChoice(val id: UUID = UUID.randomUUID(), val name: String, val intensity: Int) :
        Discipline(name)

    @Parcelize
    data class MobilityModule(
        val name: String,
        val platform: String,
        val intensity: Int,
        val hours: Int,
        val link: String = ""
    ) : Discipline(name)

    @Parcelize
    data class Elective(val name: String, var isChecked: Boolean = false) : Discipline(name)
}

class DisciplinesBundle(val list: List<Discipline.ByChoice>, var checkedIndex: Int = -1) {
}

fun List<List<Discipline.ByChoice>>.asBundlesList() = map { DisciplinesBundle(it) }