package com.example.disciplines

import android.app.Application
import com.example.disciplines.data.DisciplinesRepository


class DisciplinesApplication: Application() {
    val disciplinesRepository: DisciplinesRepository
        get() = ServiceLocator.provideDisciplinesRepository(this)
}