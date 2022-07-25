package com.example.disciplines.di

import android.app.Application
import com.example.disciplines.presentation.confirmation.ConfirmationFragment
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
    fun confirmationComponent(): ConfirmationComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application,
            @BindsInstance
            ioDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): AppComponent
    }

    fun inject(fragment: DisciplineByChoiceFragment)
    fun inject(fragment: MobilityModuleFragment)
    fun inject(fragment: ElectivesFragment)
//    fun inject(confirmationFragment: ConfirmationFragment)
}