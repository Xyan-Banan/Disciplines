package com.example.disciplines.presentation.lists.disciplinesByChoice

import com.example.disciplines.data.models.DisciplinesBundle

sealed class NavigationEvent {
    data class Can(val list: List<DisciplinesBundle>) : NavigationEvent()
    data class Not(val checked: Int, val total: Int) : NavigationEvent()
}