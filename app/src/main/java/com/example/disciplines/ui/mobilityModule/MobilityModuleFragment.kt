package com.example.disciplines.ui.mobilityModule

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.example.disciplines.R
import com.example.disciplines.data.database.MobilityModule
import com.example.disciplines.data.database.TestValues
import com.example.disciplines.databinding.MobilityModuleListBinding
import com.example.disciplines.ui.setMobilityModule

class MobilityModuleFragment : Fragment() {
    private lateinit var binding: MobilityModuleListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val list = TestValues.generateMobilityModules(10)
        binding = MobilityModuleListBinding.inflate(inflater)

        binding.instructions.text = getHeader(list)

        binding.radioGroup2.apply {
            removeAllViews()
            for (item in list) {
                val btn = RadioButton(context)
                btn.applyStyle()
                btn.setMobilityModule(item)
                addView(btn)
            }
        }
        return binding.root
    }

    private fun getHeader(list: List<MobilityModule>) = resources.getString(
        when (list.isEmpty()) {
            true -> R.string.instructions_mobilityModule_empty
            false -> R.string.instructions_mobilityModule
        }
    )


    private fun RadioButton.applyStyle() {
        layoutParams = RadioGroup.LayoutParams(
            RadioGroup.LayoutParams.MATCH_PARENT,
            RadioGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            val dpRatio = context.resources.displayMetrics.density
            fun dpToPX(dp: Int) = (dp * dpRatio).toInt()
            setMargins(0, 0, 0, (10 * dpRatio).toInt())
            val paddingStart = dpToPX(5)
//            val paddingBottom = dpToPX(5)
            val padding = dpToPX(10)
            setPadding(paddingStart, padding, padding, padding)
        }

        background = ResourcesCompat.getDrawable(
            resources,
            R.drawable.border_polytech_main,
            context.theme
        )
        setTextColor(
            ResourcesCompat.getColor(
                resources,
                R.color.polytechFontColor,
                context.theme
            )
        )

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            buttonDrawable?.let {
                DrawableCompat.setTint(
                    it,
                    ResourcesCompat.getColor(
                        resources,
                        R.color.polytechFontColor,
                        context.theme
                    )
                )
            }
        }
        gravity = Gravity.TOP

    }
}