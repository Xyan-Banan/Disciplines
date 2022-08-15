package com.example.disciplines.presentation.util

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import com.example.disciplines.data.models.Discipline


object DisciplinesTextFormatter {
    fun from(discipline: Discipline): CharSequence {
        return when (discipline) {
            is Discipline.MobilityModule -> from(discipline)
            is Discipline.ByChoice -> from(discipline)
            is Discipline.Elective -> from(discipline)
        }
    }

    fun from(module: Discipline.MobilityModule): CharSequence {
        return SpannableStringBuilder()
            .append(module.name, RelativeSizeSpan(1.2f), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            .appendLine()
            .appendLine(INTENSITY.format(module.intensity, module.intensity * 36))
            .append(PLATFORM.format(module.platform))
                as SpannableStringBuilder
    }

    fun from(byChoice: Discipline.ByChoice): CharSequence {
        return "${byChoice.name} (${
            INTENSITY_SHORT.format(byChoice.intensity, byChoice.intensity * 36)
        })"
    }

    fun from(elective: Discipline.Elective): CharSequence {
        return SpannableStringBuilder()
            .append(
                elective.name,
                RelativeSizeSpan(1.2f),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            .appendLine()
            .append(INTENSITY.format(elective.intensity, elective.intensity * 36))
                as SpannableStringBuilder
    }

    private const val INTENSITY = "Трудоемкость: %d з.е./%d ч."
    private const val INTENSITY_SHORT = "%d з.е./%d ч."
    private const val PLATFORM = "Платформа: %s"
}