package com.example.disciplines.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Singleton
@Component(modules = [DefaultDisciplinesRepositoryImplModule::class, RemoteDataSourceModule::class, ApiModule::class])
interface AppComponent {
    fun confirmationComponent(): ViewModelsComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance
            application: Application,
            @BindsInstance
            ioDispatcher: CoroutineDispatcher = Dispatchers.IO
        ): AppComponent
    }
}