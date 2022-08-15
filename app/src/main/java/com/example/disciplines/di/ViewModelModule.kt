package com.example.disciplines.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.presentation.lists.disciplinesByChoice.DisciplinesByChoiceViewModel
import com.example.disciplines.presentation.lists.electives.ElectivesViewModel
import com.example.disciplines.presentation.lists.mobilityModule.MobilityModuleViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MobilityModuleViewModel::class)
    abstract fun mobilityModuleViewModel(mobilityModuleViewModel: MobilityModuleViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DisciplinesByChoiceViewModel::class)
    abstract fun disciplinesByChoiceViewModel(mobilityModuleViewModel: DisciplinesByChoiceViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ElectivesViewModel::class)
    abstract fun electivesViewModel(electivesViewModel: ElectivesViewModel): ViewModel

    @SubcomponentScope
    @Binds
    abstract fun bindViewModelFactory(factory: AppViewModelFactory): ViewModelProvider.Factory
}