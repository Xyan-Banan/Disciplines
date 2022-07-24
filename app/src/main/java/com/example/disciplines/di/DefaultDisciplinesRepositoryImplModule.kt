package com.example.disciplines.di

import com.example.disciplines.data.repositories.DefaultDisciplinesRepositoryImpl
import com.example.disciplines.domain.repositories.DisciplinesRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DefaultDisciplinesRepositoryImplModule {
    @Singleton
    @Binds
    abstract fun bindDefaultRepositoryImpl(disciplinesRepository: DefaultDisciplinesRepositoryImpl): DisciplinesRepository
}