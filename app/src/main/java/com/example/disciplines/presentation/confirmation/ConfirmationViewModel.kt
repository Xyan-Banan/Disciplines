package com.example.disciplines.presentation.confirmation

import android.app.Application
import android.net.Uri
import android.os.Build
import android.text.SpannableStringBuilder
import android.util.Log
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.disciplines.BuildConfig
import com.example.disciplines.R
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.domain.repositories.ApplicationTemplateInteractor
import com.example.disciplines.presentation.model.GroupNumberInfo
import com.example.disciplines.presentation.util.spanWithBullet
import com.itextpdf.html2pdf.ConverterProperties
import com.itextpdf.html2pdf.HtmlConverter
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class ConfirmationViewModel(
    selected: SelectedDisciplines,
    groupInfo: GroupNumberInfo,
    val app: Application
) :
    AndroidViewModel(app) {
    val title = getTitle(selected)
    val selectedText = getSelectedText(selected)
    val applicationName = getApplicationName(selected)
    lateinit var pdf: File

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    private val _pdfCreatedEvent = MutableLiveData(false)
    val pdfCreatedEvent: LiveData<Boolean>
        get() = _pdfCreatedEvent

    val pdfUri: Uri by lazy {
        FileProvider.getUriForFile(
            app,
            BuildConfig.APPLICATION_ID + ".provider",
            pdf
        )
    }

    init {
        viewModelScope.launch {
            _isLoading.value = true
            pdf = createPdf(selected, groupInfo)
            _isLoading.value = false
            _pdfCreatedEvent.value = true
        }
    }

    private suspend fun createPdf(
        selectedDisciplines: SelectedDisciplines,
        groupInfo: GroupNumberInfo
    ): File =
        withContext(Dispatchers.IO) {
            val html = getHtml(selectedDisciplines, groupInfo.semester)
            val pdf = File(app.cacheDir, CACHE_FILE_NAME)
            Log.d(ConfirmationViewModel::class.simpleName, "Created PDF: $pdf")
            val converterProperties = getConverterProperties()

            pdf.outputStream().use { outputStream ->
                HtmlConverter.convertToPdf(
                    html,
                    outputStream,
                    converterProperties
                )
            }
            return@withContext pdf
        }

    private fun getHtml(selected: SelectedDisciplines, semester: Int): String {
        val template = getTemplate(selected)

        return when (selected) {
            is SelectedDisciplines.ByChoice -> ApplicationTemplateInteractor.fillByChoiceTemplate(
                template,
                selected.bundles,
                semester
            )
            is SelectedDisciplines.Electives -> ApplicationTemplateInteractor.fillElectivesTemplate(
                template,
                selected.electives,
                semester
            )
            is SelectedDisciplines.MobilityModule -> ApplicationTemplateInteractor.fillModulesTemplate(
                template,
                selected.module,
                semester
            )
        }
    }

    private fun getConverterProperties(): ConverterProperties {
        val fonts = listOf(
            R.font.tinos,
            R.font.tinos_bold,
            R.font.tinos_italic,
            R.font.tinos_bold_italic
        )

        val fontProvider = DefaultFontProvider(true, true, true)
        fonts.forEach { font ->
            try {
                app.resources.openRawResource(font).use { stream ->
                    fontProvider.addFont(stream.readBytes())
                }
            } catch (e: Exception) {
                Log.e("ConfirmationViewModel", "Could not add font: " + e.message)
            }
        }

        return ConverterProperties()
            .setCharset(PROPERTIES_CHARSET)
            .setFontProvider(fontProvider)
    }

    private fun getTemplate(selectedDisciplines: SelectedDisciplines): String {
        val templateId = when (selectedDisciplines) {
            is SelectedDisciplines.MobilityModule -> R.raw.template_mobility_module
            is SelectedDisciplines.ByChoice -> R.raw.template_by_choice
            is SelectedDisciplines.Electives -> R.raw.template_electives
        }
        return app.resources.openRawResource(templateId)
            .use { stream -> stream.bufferedReader().use { reader -> reader.readText() } }
    }


    private fun getSelectedText(selected: SelectedDisciplines): CharSequence {
        return selected.getIterable().run {
            if (size == 1)
                first().name
            else
                asSequence().map { discipline ->
                    // if api >= 28 use BulletSpan, else simple dash
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        spanWithBullet(discipline.name, app.resources)
                    } else {
                        "- ${discipline.name}"
                    }
                }.joinTo(SpannableStringBuilder(), "\n")
        }
    }

    private fun getTitle(selectedDisciplines: SelectedDisciplines) = when (selectedDisciplines) {
        is SelectedDisciplines.MobilityModule -> R.string.title_selected_mobilityModule
        is SelectedDisciplines.ByChoice -> R.string.title_selected_disciplinesByChoice
        is SelectedDisciplines.Electives -> R.string.title_selected_electives
    }

    private fun getApplicationName(selected: SelectedDisciplines): String {
        val dateTime = getDateTime()
        val applicationType = getApplicationType(selected)
        return APPLICATION_FILE_NAME.format(applicationType, dateTime)
    }

    private fun getApplicationType(selected: SelectedDisciplines): String {
        val id = when (selected) {
            is SelectedDisciplines.ByChoice -> R.string.disciplinesByChoice
            is SelectedDisciplines.MobilityModule -> R.string.mobilityModule
            is SelectedDisciplines.Electives -> R.string.electives
        }
        return app.resources.getString(id)
    }

    private fun getDateTime(): String {
        val date = Calendar.getInstance().time
        return SimpleDateFormat(DATETIME_FORMAT, Locale.getDefault()).format(date)
    }

    fun deletePdf() {
        if (::pdf.isInitialized)
            pdf.delete()
    }

    companion object {
        private const val DATETIME_FORMAT = "dd.MM.yy_HH.mm.ss"
        private const val APPLICATION_FILE_NAME = "Заявление (%s) %s"
        private const val PROPERTIES_CHARSET = "utf8"
        private const val CACHE_FILE_NAME = "Application.pdf"
    }
}

