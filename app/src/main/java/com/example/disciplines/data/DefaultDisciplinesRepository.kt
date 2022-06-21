package com.example.disciplines.data

import com.example.disciplines.data.model.Discipline
import com.example.disciplines.data.model.DisciplinesBundle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DefaultDisciplinesRepository(
    private val disciplinesRemoteDataSource: DisciplinesDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : DisciplinesRepository {
    private val cachedDisciplines = mutableMapOf<String, List<DisciplinesBundle>>()
    private val cachedMobilityModules = mutableMapOf<String, List<Discipline.MobilityModule>>()
    private val cachedElectives = mutableMapOf<String, List<Discipline.Elective>>()

    override suspend fun getDisciplinesByChoice(groupName: String): List<DisciplinesBundle> {
        return cachedDisciplines.getOrPut(groupName.dropLast(2)) {
            withContext(ioDispatcher) {
                disciplinesRemoteDataSource.getDisciplinesByChoice(groupName)
            }
        }
    }

    override suspend fun getMobilityModules(groupName: String): List<Discipline.MobilityModule> {
        return cachedMobilityModules.getOrPut(groupName.dropLast(2)) {
            withContext(ioDispatcher) {
                disciplinesRemoteDataSource.getMobilityModules(groupName)
            }
        }
    }

    override suspend fun getElectives(groupName: String): List<Discipline.Elective> {
        return cachedElectives.getOrPut(groupName.dropLast(2)) {
            withContext(ioDispatcher) {
                disciplinesRemoteDataSource.getElectives(groupName)
            }
        }
    }
}