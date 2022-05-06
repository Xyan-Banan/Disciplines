package com.example.disciplines.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


sealed class SelectedDisciplines: Parcelable{
    @Parcelize
    data class ByChoice(val value: DisciplinesBundle): SelectedDisciplines()

    @Parcelize
    data class MobilityModule(val value: Discipline.MobilityModule):SelectedDisciplines()

    @Parcelize
    data class Electives(val value: List<Discipline.Elective>): SelectedDisciplines()
}
