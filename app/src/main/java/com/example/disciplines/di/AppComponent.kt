package com.example.disciplines.di

import com.example.disciplines.presentation.lists.disciplinesByChoice.DisciplineByChoiceFragment
import com.example.disciplines.presentation.lists.electives.ElectivesFragment
import com.example.disciplines.presentation.lists.mobilityModule.MobilityModuleFragment
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
@Component(modules = [DefaultDisciplinesRepositoryImplModule::class, RemoteDataSourceModule::class, ApiModule::class, ViewModelModule::class])
interface AppComponent {
    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            ioDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): AppComponent
    }

    fun inject(fragment: DisciplineByChoiceFragment)
    fun inject(fragment: MobilityModuleFragment)
    fun inject(fragment: ElectivesFragment)
}