package com.example.disciplines.data

import com.example.disciplines.data.model.Discipline
import com.example.disciplines.data.model.DisciplinesBundle

interface DisciplinesDataSource {
    suspend fun getDisciplinesByChoice(groupName: String): List<DisciplinesBundle>

    suspend fun getMobilityModules(groupName: String): List<Discipline.MobilityModule>

    suspend fun getElectives(groupName: String): List<Discipline.Elective>
}
