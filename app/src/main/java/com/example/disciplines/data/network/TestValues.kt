package com.example.disciplines.data.network

import com.example.disciplines.data.network.model.*
import java.util.*
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
                    UUID.randomUUID(),
                    "Дисциплина ${bStr}${it + 1}",
                    Random.nextInt(30, 150)
                )
            )
        }
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
        Discipline.Elective("Факультатив ${it + 1}")
    }
}