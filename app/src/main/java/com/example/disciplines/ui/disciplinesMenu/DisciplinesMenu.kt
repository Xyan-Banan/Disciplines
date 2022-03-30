package com.example.disciplines.ui.disciplinesMenu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.disciplines.databinding.DisciplinesMenuFragmentBinding
import com.example.disciplines.ui.CurrentGroup

class DisciplinesMenu : Fragment() {

    private lateinit var viewModel: DisciplinesMenuViewModel
    private lateinit var binding: DisciplinesMenuFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val groupNumber = CurrentGroup.value ?: "в353090490321"
        viewModel = ViewModelProvider(this).get(DisciplinesMenuViewModel::class.java)

        binding = DisciplinesMenuFragmentBinding.inflate(inflater)
        binding.disciplinesByChoiceBtn.setOnClickListener {
            findNavController().navigate(DisciplinesMenuDirections.actionDisciplinesMenuToDisciplineByChoiceFragment())
        }
        binding.mobilityModuleBtn.setOnClickListener {
            findNavController().navigate(DisciplinesMenuDirections.actionDisciplinesMenuToMobilityModuleFragment())
        }
        binding.electivesBtn.setOnClickListener {
            findNavController().navigate(DisciplinesMenuDirections.actionDisciplinesMenuToElectives())
        }

        return binding.root
    }
}