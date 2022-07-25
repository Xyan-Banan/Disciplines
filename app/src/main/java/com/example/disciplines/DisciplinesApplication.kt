package com.example.disciplines

import android.app.Application
import com.example.disciplines.di.AppComponent
import com.example.disciplines.di.ConfirmationComponent
import com.example.disciplines.di.DaggerAppComponent
import com.example.disciplines.presentation.model.GroupNumberInfo


class DisciplinesApplication : Application() {
    lateinit var component: AppComponent
        private set
    lateinit var confirmationComponent: ConfirmationComponent
        private set
    var groupInfo: GroupNumberInfo? = null
        private set

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create(this)
    }

    fun setGroupInfo(gInfo: GroupNumberInfo){
        if(groupInfo == gInfo) return

        groupInfo = gInfo
        confirmationComponent = component.confirmationComponent().create(gInfo)
    }
}