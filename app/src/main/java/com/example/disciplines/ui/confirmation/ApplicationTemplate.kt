package com.example.disciplines.ui.confirmation

import kotlinx.html.*
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.data.network.model.DisciplinesBundle
import kotlinx.html.stream.appendHTML
import java.lang.StringBuilder

class ApplicationTemplate(private val rawHtml: String) {
    fun fill(selectedDisciplines: Array<Discipline>, from: Int): String {
        val rows = when (from) {
            R.id.mobilityModuleFragment -> getModulesRows(selectedDisciplines.map { it as Discipline.MobilityModule })
            R.id.electivesFragment -> getElectivesRows(selectedDisciplines.map { it as Discipline.Elective })
            R.id.disciplineByChoiceFragment -> getByChoiceRows(selectedDisciplines.map { it as DisciplinesBundle })
            else -> throw IllegalStateException()
        }
        return rawHtml.format(rows)
    }

    private fun getByChoiceRows(bundles: Collection<DisciplinesBundle>): String {
        return StringBuilder().appendHTML().apply {
            for ((bundleIndex, bundle) in bundles.withIndex()) {
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
                        td { +discipline.name }
                        if (disciplineIndex == 0) {
                            td {
                                rowSpan = "${disciplines.size}"
                                +discipline.intensity.toString()
                            }
                            td {
                                rowSpan = "${disciplines.size}"
                                +"период"
                            }
                        }
                        td {
                            +if (disciplineIndex == checked) "✅ ✓ ✔ \uD83D\uDDF8" else ""
                        }
                    }
                }
            }
        }.finalize().toString()
    }

    private fun getElectivesRows(electives: Collection<Discipline.Elective>): String {
        return StringBuilder().appendHTML().apply {
            for (elective in electives)
                tr {
                    td { +elective.name }
                    td { +elective.intensity.toString() }
                    td { +"текущий семестр" }
                }
        }.finalize().toString()
    }

    // https://play.kotlinlang.org/koans/Builders/Html%20builders/html.kt

    private fun getModulesRows(modules: Collection<Discipline.MobilityModule>): String {
        return StringBuilder().appendHTML().apply {
            for (module in modules)
                tr {
                    td { +module.name }
                    td { +module.intensity.toString() }
                    td { +"текущий семестр" }
                    td { a(module.link) { +module.link } }
                }
        }.finalize().toString()
    }


}