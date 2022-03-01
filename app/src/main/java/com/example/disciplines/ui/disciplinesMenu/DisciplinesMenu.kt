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
    ): View? {
        binding = DisciplinesMenuFragmentBinding.inflate(inflater)
        binding.disciplinesByChoiceBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuDirections.actionDisciplinesMenuToDisciplineByChoiceFragment(
                    groupNumber
                )
            )
        }

        viewModel = ViewModelProvider(this).get(DisciplinesMenuViewModel::class.java)
        groupNumber = DisciplinesMenuArgs.fromBundle(requireArguments()).groupNumber

        return binding.root
    }
}