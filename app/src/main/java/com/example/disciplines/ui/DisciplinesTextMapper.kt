package com.example.disciplines.ui

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import com.example.disciplines.data.network.model.Discipline


object DisciplinesTextMapper {
    fun from(discipline: Discipline): CharSequence {
        return when (discipline) {
            is Discipline.MobilityModule -> from(discipline)
            is Discipline.ByChoice -> from(discipline)
            is Discipline.Elective -> from(discipline)
        }
    }

    fun from(module: Discipline.MobilityModule): CharSequence {
        return SpannableStringBuilder()
            .append(module.name + "\n", RelativeSizeSpan(1.2f), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            .append(INTENSITY.format(module.intensity, module.intensity * 36))
            .append("Платформа: ${module.platform}")
    }

    fun from(byChoice: Discipline.ByChoice): CharSequence {
        return "${byChoice.name} (${byChoice.intensity} з.е./${byChoice.intensity * 36} ч.)"
    }

    fun from(elective: Discipline.Elective): CharSequence {
        return SpannableStringBuilder()
            .append(
                elective.name + "\n",
                RelativeSizeSpan(1.2f),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            .append(INTENSITY.format(elective.intensity, elective.intensity * 36))
    }

    private const val INTENSITY = "Трудоемкость: %d з.е./%d ч."
}