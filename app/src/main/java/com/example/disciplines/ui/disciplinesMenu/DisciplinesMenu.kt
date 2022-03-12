package com.example.disciplines.ui.disciplinesMenu

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.disciplines.databinding.DisciplinesMenuFragmentBinding

class DisciplinesMenu : Fragment() {

    private lateinit var viewModel: DisciplinesMenuViewModel
    private lateinit var binding: DisciplinesMenuFragmentBinding
    private lateinit var groupNumber: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        groupNumber = try {
            DisciplinesMenuArgs.fromBundle(requireArguments()).groupNumber
        } catch (e: Exception) {
            "в353090490321"
        }
        viewModel = ViewModelProvider(this).get(DisciplinesMenuViewModel::class.java)

        binding = DisciplinesMenuFragmentBinding.inflate(inflater)
        binding.disciplinesByChoiceBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuDirections.actionDisciplinesMenuToDisciplineByChoiceFragment(
                    groupNumber
                )
            )
        }
        binding.mobilityModuleBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuDirections.actionDisciplinesMenuToMobilityModuleFragment(groupNumber)
            )
        }

        return binding.root
    }
}