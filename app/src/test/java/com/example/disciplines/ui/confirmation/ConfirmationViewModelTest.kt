package com.example.disciplines.ui.confirmation

import android.app.Application
import android.os.Build
import android.text.SpannableStringBuilder
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.spanWithBullet
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ConfirmationViewModelTest {

    @Test
    fun getTitle_fromMobilityModule_choosedModules() {
        val confirmationViewModel = ConfirmationViewModel(
            arrayOf(),
            R.id.mobilityModuleFragment,
            ApplicationProvider.getApplicationContext()
        )

        val title = confirmationViewModel.title

        assertEquals(R.string.title_selected_mobilityModule, title)
    }

    @Test
    fun getSelectedText_ThreeDisciplines_spannedOrDashed() {
        val disciplines = Array<Discipline>(3) { Discipline.Elective("Фак-тив ${it + 1}") }
        val confirmationViewModel = ConfirmationViewModel(
            disciplines,
            R.id.mobilityModuleFragment,
            ApplicationProvider.getApplicationContext()
        )

        val selectedText = confirmationViewModel.selectedText
        val strDisciplines = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P)
            disciplines.asSequence().joinTo(SpannableStringBuilder(), "\n") {
                spanWithBullet(it.name, ApplicationProvider.getApplicationContext<Application>().resources)
            }
        else
            disciplines.joinToString("\n") { "- ${it.name}" }

        assert(strDisciplines.contentEquals(selectedText))
    }
}