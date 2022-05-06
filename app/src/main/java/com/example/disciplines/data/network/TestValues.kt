package com.example.disciplines.data.network

import com.example.disciplines.data.network.model.*
import kotlin.random.Random

object TestValues {
    fun generateDisciplinesBundles(count: Int) = List(count) { bundleIndex ->
        DisciplinesBundle(
            generateDisciplinesList(Random.nextInt(2, 5), bundleIndex + 1)
        )
    }

    fun generateDisciplinesList(count: Int, bundleIndex: Int? = null) = buildList {
        val bStr = bundleIndex?.let { "$it." } ?: ""
        repeat(count) {
            add(
                Discipline.ByChoice(
                    "Дисциплина ${bStr}${it + 1}",
                    Random.nextInt(30, 150)
                )
            )
        }
    }

    fun generateMobilityModules(count: Int) = List(count) {
        val intensity = Random.nextInt(3, 7)
        Discipline.MobilityModule(
            "Модуль мобильности ${it + 1}",
            intensity,
            listOf("НПОО", "СДО").random(),
            intensity * 36
        )
    }

    fun generateElectives(count: Int) = List(count) {
        Discipline.Elective("Факультатив ${it + 1}", Random.nextInt(3, 6))
    }
}