package com.example.disciplines.ui.mobilityModule

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.disciplines.databinding.MobilityModuleListBinding

class MobilityModuleFragment : Fragment() {
    private lateinit var binding: MobilityModuleListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val list = TestValues.generateMobilityModules(5)
        val groupName = MobilityModuleFragmentArgs.fromBundle(requireArguments()).groupName
        val viewModel: MobilityModuleViewModel by viewModels {
            MobilityModuleViewModelFactory(groupName)
        }
        binding = MobilityModuleListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.confirmBtn.setOnClickListener {
            val checkedId = binding.radioGroup2.checkedRadioButtonId //это именно id, а не индекс!!!
            val text =
                if (checkedId >= 0)
                    "Выбран модуль мобильности $checkedId"
                else
                    "Необходимо выбрать хотя бы один модуль мобильности"
            val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        return binding.root
    }
}