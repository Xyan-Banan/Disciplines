package com.example.disciplines.ui.confirmation

import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.data.network.model.DisciplinesBundle
import com.example.disciplines.data.network.model.SelectedDisciplines
import kotlinx.html.a
import kotlinx.html.i
import kotlinx.html.stream.appendHTML
import kotlinx.html.td
import kotlinx.html.tr

class ApplicationTemplate(private val rawHtml: String) {
    fun fill(selectedDisciplines: SelectedDisciplines, semester: String): String {
        val rows = when (selectedDisciplines) {
            is SelectedDisciplines.MobilityModule -> getModulesRows(selectedDisciplines.module, semester)
            is SelectedDisciplines.Electives -> getElectivesRows(selectedDisciplines.electives, semester)
            is SelectedDisciplines.ByChoice -> getByChoiceRows(selectedDisciplines.bundles, semester)
        }
        return rawHtml.format(rows)
    }

    private fun getByChoiceRows(bundles: Collection<DisciplinesBundle>, semester: String): String {
        return StringBuilder().appendHTML().apply {
            for ((bundleIndex, bundle) in bundles.withIndex()) {
                //разделитель блоков
                tr {
                    td {
                        colSpan = "4"
                        i{ +"Блок дисциплин во выбору ${bundleIndex + 1}" }
                    }
                }
                val disciplines = bundle.list
                val checked = bundle.checkedIndex
                for ((disciplineIndex, discipline) in disciplines.withIndex()) {
                    tr {
                        //название дисциплины
                        td { +discipline.name }
                        if (disciplineIndex == 0) {
                            //трудоемкость (для всего блока одинаковая)
                            td {
                                rowSpan = "${disciplines.size}"
                                +discipline.intensity.toString()
                            }
                            //период изучения (для всего блока одинаковый)
                            td {
                                rowSpan = "${disciplines.size}"
                                +semester
                            }
                        }
                        //отметка о выборе
                        td {
                            +if (disciplineIndex == checked) "√ ✓ ✔ \uD83D\uDDF8" else ""
                        }
                    }
                }
            }
        }.finalize().toString()
    }

    private fun getElectivesRows(electives: Collection<Discipline.Elective>, semester: String): String {
        return StringBuilder().appendHTML().apply {
            for (elective in electives)
                tr {
                    td { +elective.name }
                    td { +elective.intensity.toString() }
                    td { +semester }
                }
        }.finalize().toString()
    }

    private fun getModulesRows(module: Discipline.MobilityModule, semester: String): String {
        return StringBuilder().appendHTML().tr {
                    td { +module.name }
                    td { +module.intensity.toString() }
                    td { +semester }
                    td { a(module.link) { +module.link } }
                }.toString()
    }
}