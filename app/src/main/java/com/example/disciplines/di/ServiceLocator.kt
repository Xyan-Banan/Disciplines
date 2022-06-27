package com.example.disciplines.di

import android.content.Context
import com.example.disciplines.DisciplinesApplication
import com.example.disciplines.data.repositories.DefaultDisciplinesRepositoryImpl
import com.example.disciplines.domain.repositories.DisciplinesRepository
import com.example.disciplines.data.source.network.DisciplinesRemoteDataSource

object ServiceLocator {
    var disciplinesRepository: DisciplinesRepository? = null

    fun provideDisciplinesRepository(context: Context): DisciplinesRepository {
        synchronized(this){
            return disciplinesRepository ?: createDisciplinesRepository(context)
        }
    }

    private fun createDisciplinesRepository(context: Context): DisciplinesRepository {
        return (context.applicationContext as DisciplinesApplication).disciplinesRepository
//        val newRepo = DefaultDisciplinesRepositoryImpl(DisciplinesRemoteDataSource())
//        disciplinesRepository = newRepo
//        return newRepo
    }
}
