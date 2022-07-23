package com.example.disciplines.domain.repositories

import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.models.DisciplinesBundle
import kotlinx.html.a
import kotlinx.html.i
import kotlinx.html.stream.appendHTML
import kotlinx.html.td
import kotlinx.html.tr

object ApplicationTemplateInteractor {

    fun fillByChoiceTemplate(
        template: String,
        bundles: Collection<DisciplinesBundle>,
        semester: Int
    ): String {
        val table = StringBuilder().appendHTML().apply {
            for ((bundleIndex, bundle) in bundles.withIndex()) {
                //разделитель блоков
                tr {
                    td {
                        colSpan = DEFAULT_COLSPAN
                        i { +BLOCK_TITLE.format(bundleIndex + 1) }
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
                                +semester.toString()
                            }
                        }
                        //отметка о выборе
                        td {
                            +if (disciplineIndex == checked) CHECKED else NOT_CHECKED
                        }
                    }
                }
            }
        }.finalize().toString()

        return template.format(table)
    }

    fun fillElectivesTemplate(
        template: String,
        electives: Collection<Discipline.Elective>,
        semester: Int
    ): String {
        val table = StringBuilder().appendHTML().apply {
            for (elective in electives)
                tr {
                    td { +elective.name }
                    td { +elective.intensity.toString() }
                    td { +semester.toString() }
                }
        }.finalize().toString()
        return template.format(table)
    }

    fun fillModulesTemplate(template: String, module: Discipline.MobilityModule, semester: Int): String {
        val table = StringBuilder().appendHTML().tr {
            td { +module.name }
            td { +module.intensity.toString() }
            td { +semester.toString() }
            td { a(module.link) { +module.link } }
        }.toString()
        return template.format(table)
    }

    private const val DEFAULT_COLSPAN = "4"
    private const val CHECKED = "√"
    private const val NOT_CHECKED = ""
    private const val BLOCK_TITLE = "Блок дисциплин во выбору %d"
}