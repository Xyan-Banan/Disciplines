package com.example.disciplines.data.network.model

data class Discipline(val id: Int, val name: String, val hours: Int)

data class DisciplinesPair(
    val first: Discipline,
    val second: Discipline,
    var checked: Checked = Checked.None
) {
    enum class Checked {
        First,
        Second,
        None
    }
}