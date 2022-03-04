package com.example.disciplines.data.database

data class DisciplinesPair(val first: Discipline, val second: Discipline, var checked: Checked = Checked.None) {
    enum class Checked{
        First,
        Second,
        None
    }
}