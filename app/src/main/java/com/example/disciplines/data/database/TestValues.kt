package com.example.disciplines.data.database

import kotlin.random.Random

object TestValues {
    fun generateDisciplinesByChoice(count: Int) = List(count) {
        DisciplinesPair(
            Discipline(Random.nextInt(), "Дисциплина ${it + 1}.1", Random.nextInt(150)),
            Discipline(Random.nextInt(), "Дисциплина ${it + 1}.2", Random.nextInt(150))
        )
    }
}