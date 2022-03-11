package com.example.disciplines.ui

import android.os.Build
import android.text.*
import android.text.style.BulletSpan
import android.text.style.RelativeSizeSpan
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.set
import androidx.databinding.BindingAdapter
import com.example.disciplines.R
import com.example.disciplines.data.database.Discipline
import com.example.disciplines.data.database.DisciplinesPair
import com.example.disciplines.data.database.MobilityModule

@BindingAdapter("discipline")
fun RadioButton.setDiscipline(discipline: Discipline?) {
    discipline?.apply {
        text = resources.getString(R.string.discipline, name, hours)
    }
}

@BindingAdapter("mobilityModule")
fun RadioButton.setMobilityModule(mobilityModule: MobilityModule?) {
    mobilityModule?.apply {
        val bulletRadius = (textSize / resources.displayMetrics.scaledDensity * 0.5f).toInt()
        val gapWidth = 20
        fun getBulletSpan() =
//          if api >= 28 use BulletSpan with radius, else without
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                BulletSpan(
                    gapWidth,
                    ResourcesCompat.getColor(
                        resources,
                        R.color.polytechFontColor,
                        context.theme
                    ),
                    bulletRadius
                )
            } else {
                BulletSpan(
                    gapWidth,
                    ResourcesCompat.getColor(
                        resources,
                        R.color.polytechFontColor,
                        context.theme
                    )
                )
            }

        val stringBuilder = SpannableStringBuilder()
            .append(name, RelativeSizeSpan(1.2f), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            .append(" ($intensity зачетных единиц)\n")
            .append("Входящие дисциплины:\n")
        disciplines.asSequence()
            .map {
                SpannableString("${it.name} (${it.hours} часов)").apply {
                    set(
                        0,
                        length,
                        getBulletSpan()
                    )
                }
            }.joinTo(stringBuilder, "\n")

        text = stringBuilder
    }
}

@BindingAdapter("disciplinesPair")
fun RadioGroup.setDisciplinesPair(disciplinesPair: DisciplinesPair?) {
    disciplinesPair ?: return

    when (disciplinesPair.checked) {
        DisciplinesPair.Checked.None -> clearCheck()
        DisciplinesPair.Checked.First -> check(R.id.disciplineRadio1)
        DisciplinesPair.Checked.Second -> check(R.id.disciplineRadio2)
    }
}