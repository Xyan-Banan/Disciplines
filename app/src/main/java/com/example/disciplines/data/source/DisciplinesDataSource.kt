package com.example.disciplines.data.source

import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.models.DisciplinesBundle

interface DisciplinesDataSource {
    suspend fun getDisciplinesByChoice(groupName: String): List<DisciplinesBundle>

    suspend fun getMobilityModules(groupName: String): List<Discipline.MobilityModule>

    suspend fun getElectives(groupName: String): List<Discipline.Elective>
}
