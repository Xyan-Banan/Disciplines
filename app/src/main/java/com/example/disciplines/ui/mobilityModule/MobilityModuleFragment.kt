package com.example.disciplines.ui.mobilityModule

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.disciplines.R
import com.example.disciplines.applyGravity
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.databinding.MobilityModuleListBinding

class MobilityModuleFragment : Fragment() {
    private lateinit var binding: MobilityModuleListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val list = TestValues.generateMobilityModules(5)
        val viewModel: MobilityModuleViewModel by viewModels() //{ MobilityModuleViewModelFactory(groupName) }
        binding = MobilityModuleListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.confirmBtn.setOnClickListener {
            val checkedId = binding.radioGroup2.checkedRadioButtonId //это именно id, а не индекс!!!

            if (checkedId >= 0) {
                println("Выбран модуль мобильности $checkedId")
                val checkedRadioButton = binding.radioGroup2.findViewById<RadioButton>(checkedId)
                val checkedIndex = binding.radioGroup2.indexOfChild(checkedRadioButton)
                val checked = arrayOf<Discipline>(viewModel.modulesList.value!![checkedIndex])
                findNavController().navigate(
                    MobilityModuleFragmentDirections.actionMobilityModuleFragmentToConfirmationFragment(
                        checked
                    )
                )

            } else {
                val text = getString(R.string.toast_text_mobilityModule)
                Toast.makeText(context, text, Toast.LENGTH_LONG)
                    .applyGravity(Gravity.CENTER, 0, 0)
                    .show()
            }
        }
        return binding.root
    }
}