package com.example.disciplines.ui.lists.electives

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.disciplines.DisciplinesApplication
import com.example.disciplines.R
import com.example.disciplines.applyGravity
import com.example.disciplines.data.model.SelectedDisciplines
import com.example.disciplines.databinding.ElectiveListBinding

class ElectivesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val groupInfo = ElectivesFragmentArgs.fromBundle(requireArguments()).groupInfo
        val viewModel: ElectivesViewModel by viewModels {
            ElectivesViewModelFactory(
                requireActivity().application,
                (requireContext().applicationContext as DisciplinesApplication).disciplinesRepository,
                groupInfo
            )
        }
        val binding = ElectiveListBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.confirmBtn.setOnClickListener {
            val checked = viewModel.electivesList.value!!.filter { it.isChecked }

            if (checked.isNotEmpty())
                findNavController().navigate(
                    ElectivesFragmentDirections.actionElectivesToConfirmationFragment(
                        SelectedDisciplines.Electives(checked), groupInfo
                    )
                )
            else {
                Toast.makeText(
                    context,
                    getString(R.string.toast_text_electives),
                    Toast.LENGTH_LONG
                ).applyGravity(Gravity.CENTER, 0, 0)
                    .show()
            }
        }

        return binding.root
    }
}