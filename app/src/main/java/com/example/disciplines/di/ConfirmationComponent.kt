package com.example.disciplines.di

import com.example.disciplines.presentation.confirmation.ConfirmationFragment
import com.example.disciplines.presentation.model.GroupNumberInfo
import dagger.BindsInstance
import dagger.Subcomponent

@Subcomponent
interface ConfirmationComponent {
    @Subcomponent.Factory
    interface Factory {
        fun create(
            @BindsInstance
            groupInfo: GroupNumberInfo
        ): ConfirmationComponent
    }

    fun inject(confirmationFragment: ConfirmationFragment)
}