package com.example.disciplines.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed class SelectedDisciplines: Parcelable{
    abstract fun getIterable(): Collection<Discipline>

    @Parcelize
    data class ByChoice(val bundles: List<DisciplinesBundle>): SelectedDisciplines() {
        override fun getIterable(): Collection<Discipline> = bundles.map { it.list[it.checkedIndex] }
    }

    @Parcelize
    data class MobilityModule(val module: Discipline.MobilityModule):SelectedDisciplines(){
        override fun getIterable(): Collection<Discipline> = listOf(module)
    }

    @Parcelize
    data class Electives(val electives: List<Discipline.Elective>): SelectedDisciplines(){
        override fun getIterable(): Collection<Discipline> = electives
    }
}
