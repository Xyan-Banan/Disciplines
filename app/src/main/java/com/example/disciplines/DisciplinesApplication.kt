package com.example.disciplines

import android.app.Application
import com.example.disciplines.di.AppComponent
import com.example.disciplines.di.DaggerAppComponent


class DisciplinesApplication : Application() {
    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.factory().create()
    }
}