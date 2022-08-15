package com.example.disciplines.data.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Discipline : Parcelable {
    /**
     * Название
     */
    abstract val name: String

    /**
     * Трудоемкость
     */
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
        var isSelected: Boolean = false
    ) : Discipline()
}

@Parcelize
class DisciplinesBundle(
    val list: List<Discipline.ByChoice>,
    var checkedIndex: Int = -1
) :
    Parcelable {
    constructor(list: List<Discipline.ByChoice>) : this(list.sortedBy { it.name }, -1)
}

fun List<List<Discipline.ByChoice>>.asBundlesList() = map { DisciplinesBundle(it) }