package com.example.disciplines

import android.app.Application
import com.example.disciplines.di.AppComponent
import com.example.disciplines.di.ViewModelsComponent
import com.example.disciplines.di.DaggerAppComponent
import com.example.disciplines.presentation.model.GroupNumberInfo


class DisciplinesApplication : Application() {
    lateinit var component: AppComponent
        private set
    var viewModelsComponent: ViewModelsComponent? = null
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
    }

    fun setGroupInfo(gInfo: GroupNumberInfo) {
        if (viewModelsComponent?.groupInfo == gInfo) return

        viewModelsComponent = component.confirmationComponent().create(gInfo)
    }
}