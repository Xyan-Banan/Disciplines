package com.example.disciplines.di

import com.example.disciplines.presentation.confirmation.ConfirmationFragment
import com.example.disciplines.presentation.lists.disciplinesByChoice.DisciplineByChoiceFragment
import com.example.disciplines.presentation.lists.electives.ElectivesFragment
import com.example.disciplines.presentation.lists.mobilityModule.MobilityModuleFragment
import com.example.disciplines.presentation.model.GroupNumberInfo
import dagger.BindsInstance
import dagger.Subcomponent

@SubcomponentScope
@Subcomponent(modules = [ViewModelModule::class])
interface ViewModelsComponent {
    val groupInfo: GroupNumberInfo

    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance
            groupInfo: GroupNumberInfo
        ): ViewModelsComponent
    }

    fun inject(confirmationFragment: ConfirmationFragment)
    fun inject(confirmationFragment: DisciplineByChoiceFragment)
    fun inject(mobilityModuleFragment: MobilityModuleFragment)
    fun inject(fragment: ElectivesFragment)
}