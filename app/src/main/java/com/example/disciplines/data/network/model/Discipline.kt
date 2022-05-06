package com.example.disciplines.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Discipline : Parcelable {
    abstract val name: String
    abstract val intensity: Int

    @Parcelize
    data class ByChoice(
        override val name: String,
        override val intensity: Int
    ) : Discipline()

    @Parcelize
    data class MobilityModule(
        override val name: String,
        override val intensity: Int,
        val platform: String,
        val hours: Int,
        val link: String = ""
    ) : Discipline()

    @Parcelize
    data class Elective(
        override val name: String,
        override val intensity: Int = 0,
        var isChecked: Boolean = false
    ) : Discipline()
}

@Parcelize
data class DisciplinesBundle(val list: List<Discipline.ByChoice>, var checkedIndex: Int = -1) :
    Parcelable

fun List<List<Discipline.ByChoice>>.asBundlesList() = map { DisciplinesBundle(it) }