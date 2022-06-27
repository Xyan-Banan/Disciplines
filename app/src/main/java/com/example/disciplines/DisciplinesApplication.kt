package com.example.disciplines

import android.app.Application
import com.example.disciplines.di.DaggerRepositoryComponent
import com.example.disciplines.di.RepositoryComponent
import com.example.disciplines.di.ServiceLocator
import com.example.disciplines.domain.repositories.DisciplinesRepository
import kotlinx.coroutines.Dispatchers


class DisciplinesApplication : Application() {
    lateinit var disciplinesRepository: DisciplinesRepository
        private set

    override fun onCreate() {
        super.onCreate()
        disciplinesRepository = DaggerRepositoryComponent.builder()
            .ioDispatcher(Dispatchers.IO)
            .build()
            .getDefaultRepository()
    }


}