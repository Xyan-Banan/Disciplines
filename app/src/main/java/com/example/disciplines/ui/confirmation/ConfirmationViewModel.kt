package com.example.disciplines.ui.confirmation

import android.app.Application
import android.os.Build
import android.text.SpannableStringBuilder
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.spanWithBullet
import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class ConfirmationViewModel(
    selectedDisciplines: Array<Discipline>,
    from: Int,
    val app: Application
) :
    AndroidViewModel(app) {
    val title = getTitle(from)
    val selectedText = getSelectedText(selectedDisciplines)
    lateinit var pdf: File
    val showLoading = MutableLiveData(false)

    init {
        viewModelScope.launch {
            showLoading.value = true
            pdf = getPdf(selectedDisciplines, from)
            showLoading.value = false
        }
    }

    private suspend fun getPdf(selectedDisciplines: Array<Discipline>, from: Int): File =
        withContext(Dispatchers.IO) {
            val template = ApplicationTemplate(getHtml(from))
            val filled = template.fill(selectedDisciplines, from)
            val pdf = File(app.cacheDir, "cache.pdf")
            pdf.outputStream().use {
                HtmlConverter.convertToPdf(
                    filled,
                    it,
                    ConverterProperties()
                        .setFontProvider(
                            DefaultFontProvider(
                                true,
                                true,
                                true
                            )
                        )
                )
            }
            return@withContext pdf
        }

    private fun getHtml(from: Int): String {
        val templateId = when (from) {
            R.id.mobilityModuleFragment -> R.raw.template_mobility_module
            R.id.disciplineByChoiceFragment -> R.raw.test
            R.id.electivesFragment -> R.raw.template_electives
            else -> throw IllegalStateException()
        }
        return app.resources.openRawResource(templateId)
            .use { it.bufferedReader().use { it.readText() } }
    }


    private fun getSelectedText(selected: Array<Discipline>): CharSequence {
        return if (selected.size == 1) selected.first().name
        else selected.asSequence().map {
            // if api >= 28 use BulletSpan, else simple dash
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                spanWithBullet(it.name, app.resources)
            } else {
                "- ${it.name}"
            }
        }.joinTo(SpannableStringBuilder(), "\n")
    }

    private fun getTitle(from: Int) = when (from) {
        R.id.mobilityModuleFragment -> R.string.title_selected_mobilityModule
        R.id.disciplineByChoiceFragment -> R.string.title_selected_disciplinesByChoice
        R.id.electivesFragment -> R.string.title_selected_electives
        else -> throw IllegalStateException()
    }
}

