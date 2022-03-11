package com.example.disciplines.data.database

import kotlin.random.Random
import kotlin.random.nextUInt

object TestValues {
    fun generateDisciplinesPairs(count: Int) = List(count) {
        DisciplinesPair(
            Discipline(Random.nextUInt(), "Дисциплина ${it + 1}.1", Random.nextInt(150)),
            Discipline(Random.nextUInt(), "Дисциплина ${it + 1}.2", Random.nextInt(150))
        )
    }

    fun genereateDisciplinesList(count: Int) =
        List(count) {
            Discipline(
                Random.nextUInt(),
                "Дисциплина ${it + 1}",
                Random.nextInt(30, 150)
            )
        }

    fun generateMobilityModules(count: Int) = List(count) {
        MobilityModule(
            "Модуль мобильности ${it + 1}",
            Random.nextInt(1, 6),
            genereateDisciplinesList(
                Random.nextInt(3, 8)
            )
        )
    }
}