package com.example.disciplines

import android.content.Context
import com.example.disciplines.data.DefaultDisciplinesRepository
import com.example.disciplines.data.DisciplinesRepository
import com.example.disciplines.data.source.network.DisciplinesRemoteDataSource

object ServiceLocator {
    var disciplinesRepository: DisciplinesRepository? = null

    fun provideDisciplinesRepository(context: Context): DisciplinesRepository{
        synchronized(this){
            return disciplinesRepository ?: createDisciplinesRepository(context)
        }
    }

    private fun createDisciplinesRepository(context: Context): DisciplinesRepository {
        val newRepo = DefaultDisciplinesRepository(DisciplinesRemoteDataSource)
        disciplinesRepository = newRepo
        return newRepo
    }
}
