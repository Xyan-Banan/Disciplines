package com.example.disciplines.di

import com.example.disciplines.data.repositories.DefaultDisciplinesRepositoryImpl
import com.example.disciplines.domain.repositories.DisciplinesRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DefaultDisciplinesRepositoryImplModule {
    @Binds
    abstract fun bindDefaultRepositoryImpl(disciplinesRepository: DefaultDisciplinesRepositoryImpl): DisciplinesRepository
}