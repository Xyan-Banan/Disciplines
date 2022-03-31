package com.example.disciplines.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.*


sealed class Discipline : Parcelable {
    abstract val name: String

    @Parcelize
    class ByChoice(
        val id: UUID = UUID.randomUUID(),
        override val name: String,
        val intensity: Int
    ) : Discipline()

    @Parcelize
    data class MobilityModule(
        override val name: String,
        val platform: String,
        val intensity: Int,
        val hours: Int,
        val link: String = ""
    ) : Discipline()

    @Parcelize
    data class Elective(override val name: String, var isChecked: Boolean = false) : Discipline()
}

class DisciplinesBundle(val list: List<Discipline.ByChoice>, var checkedIndex: Int = -1)

fun List<List<Discipline.ByChoice>>.asBundlesList() = map { DisciplinesBundle(it) }