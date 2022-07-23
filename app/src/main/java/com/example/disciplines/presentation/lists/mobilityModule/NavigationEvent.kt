package com.example.disciplines.presentation.lists.mobilityModule

import com.example.disciplines.data.models.Discipline

sealed class NavigationEvent {
    data class Can(val module: Discipline.MobilityModule): NavigationEvent()
    object Not: NavigationEvent()
}
