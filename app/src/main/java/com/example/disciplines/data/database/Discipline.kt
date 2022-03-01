package com.example.disciplines.data.database


data class Discipline(val id: Int, val name:String, val hours: Int){
    infix fun to (other: Discipline) = DisciplinesPair(this,other)
}

