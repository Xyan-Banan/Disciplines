package com.example.disciplines.data.network

import com.example.disciplines.data.network.model.*
import kotlin.random.Random

object TestValues {
    fun generateDisciplinesBundles(count: Int) = List(count) { bundleIndex ->
        DisciplinesBundle(
            buildList {
                repeat(Random.nextInt(2, 5)) { disciplineIndex ->
                    add(
                        DisciplineS.ByChoice(
                            Random.nextInt(),
                            "Дисциплина ${bundleIndex + 1}.${disciplineIndex + 1}",
                            Random.nextInt(1, 5) * 36
                        )
                    )
                }
            }
        )
    }

    fun generateDisciplinesList(count: Int) =
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

    fun generateElectives(count: Int) = List(count) {
        Elective("Факультатив ${it + 1}")
    }
}