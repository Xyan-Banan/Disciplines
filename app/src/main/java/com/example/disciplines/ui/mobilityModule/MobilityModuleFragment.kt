package com.example.disciplines.ui.mobilityModule

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disciplines.R
import com.example.disciplines.data.database.MobilityModule
import com.example.disciplines.data.database.TestValues
import com.example.disciplines.databinding.MobilityModuleItemBinding
import com.example.disciplines.databinding.MobilityModuleListBinding

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
            for (item in list) {
                val btn = MobilityModuleItemBinding.inflate(inflater, binding.radioGroup2, false)
                btn.mobilityModule = item
//                btn.root.setOnClickListener { Log.i("TAG", "${it.id} $it") }
                addView(btn.root)
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
}