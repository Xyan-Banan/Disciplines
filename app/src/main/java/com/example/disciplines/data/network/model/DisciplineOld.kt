package com.example.disciplines.data.network.model

data class DisciplineOld(val id: Int, val name: String, val hours: Int)

data class DisciplinesPair(
    val first: DisciplineOld,
    val second: DisciplineOld,
    var checked: Checked = Checked.None
) {
    enum class Checked {
        First,
        Second,
        None
    }
}