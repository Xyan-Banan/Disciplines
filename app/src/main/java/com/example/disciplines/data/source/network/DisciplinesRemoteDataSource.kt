package com.example.disciplines.data.source.network

import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.models.DisciplinesBundle
import com.example.disciplines.data.models.asBundlesList
import com.example.disciplines.data.source.DisciplinesDataSource
import javax.inject.Inject

class DisciplinesRemoteDataSource @Inject constructor(private val api: API) :
    DisciplinesDataSource {

    override suspend fun getDisciplinesByChoice(groupName: String): List<DisciplinesBundle> {
        return api.getDisciplinesByChoice(groupName).asBundlesList()
    }

    override suspend fun getMobilityModules(groupName: String): List<Discipline.MobilityModule> {
        return api.getMobilityModules(groupName)
    }

    override suspend fun getElectives(groupName: String): List<Discipline.Elective> {
        return api.getElectives(groupName)
    }
}