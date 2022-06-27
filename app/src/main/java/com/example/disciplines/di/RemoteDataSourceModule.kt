package com.example.disciplines.di

import com.example.disciplines.data.source.DisciplinesDataSource
import com.example.disciplines.data.source.network.DisciplinesRemoteDataSource
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RemoteDataSourceModule {
    @Binds
    abstract fun bindDataSource(dataSourceModule: DisciplinesRemoteDataSource): DisciplinesDataSource
}