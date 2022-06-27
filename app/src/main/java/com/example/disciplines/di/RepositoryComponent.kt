package com.example.disciplines.di

import androidx.lifecycle.ViewModel
import com.example.disciplines.data.repositories.DefaultDisciplinesRepositoryImpl
import com.example.disciplines.presentation.lists.disciplinesByChoice.DisciplinesByChoiceViewModel
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
@Component(modules = [RemoteDataSourceModule::class, ApiModule::class])
interface RepositoryComponent {
    fun getDefaultRepository(): DefaultDisciplinesRepositoryImpl

    @Component.Builder
    interface Builder {
        fun build(): RepositoryComponent

        @BindsInstance
        fun ioDispatcher(ioDispatcher: CoroutineDispatcher = Dispatchers.IO): Builder
    }

    fun inject(viewModel: DisciplinesByChoiceViewModel)
}