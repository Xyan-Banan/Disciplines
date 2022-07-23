package com.example.disciplines.data.repositories

import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.models.DisciplinesBundle
import com.example.disciplines.data.source.DisciplinesDataSource
import com.example.disciplines.domain.repositories.DisciplinesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class DefaultDisciplinesRepositoryImpl
@Inject constructor(
    private val disciplinesRemoteDataSource: DisciplinesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DisciplinesRepository {
    private val cachedDisciplines = mutableMapOf<String, List<DisciplinesBundle>>()
    private val cachedMobilityModules = mutableMapOf<String, List<Discipline.MobilityModule>>()
    private val cachedElectives = mutableMapOf<String, List<Discipline.Elective>>()

    override suspend fun getDisciplinesByChoice(groupName: String): List<DisciplinesBundle> {
        return cachedDisciplines.getOrPut(groupName.dropLast(2)) {
            disciplinesRemoteDataSource.getDisciplinesByChoice(groupName)
        }
    }

    override suspend fun getMobilityModules(groupName: String): List<Discipline.MobilityModule> {
        return cachedMobilityModules.getOrPut(groupName.dropLast(2)) {
            disciplinesRemoteDataSource.getMobilityModules(groupName)
        }
    }

    override suspend fun getElectives(groupName: String): List<Discipline.Elective> {
        return cachedElectives.getOrPut(groupName.dropLast(2)) {
            disciplinesRemoteDataSource.getElectives(groupName)
        }
    }
}