package com.example.disciplines.data.network.model

data class DisciplinesPair(val first: Discipline, val second: Discipline, var checked: Checked = Checked.None) {
    enum class Checked{
        First,
        Second,
        None
    }
}