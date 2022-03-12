package com.example.disciplines.data.network

import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.data.network.model.DisciplinesPair
import com.example.disciplines.data.network.model.MobilityModule
import kotlin.random.Random

object TestValues {
    fun generateDisciplinesPairs(count: Int) = List(count) {
        DisciplinesPair(
            Discipline(Random.nextInt(), "Дисциплина ${it + 1}.1", Random.nextInt(150)),
            Discipline(Random.nextInt(), "Дисциплина ${it + 1}.2", Random.nextInt(150))
        )
    }

    fun genereateDisciplinesList(count: Int) =
        List(count) {
            Discipline(
                Random.nextInt(),
                "Дисциплина ${it + 1}",
                Random.nextInt(30, 150)
            )
        }

    fun generateMobilityModules(count: Int) = List(count) {
        MobilityModule(
            "Модуль мобильности ${it + 1}",
            listOf("НПОО", "СДО").random(),
            it % 3 + 3,
            (it % 3 + 3) * 36
        )
    }
}