package com.example.disciplines.ui.disciplinesMenu

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.disciplines.databinding.DisciplinesMenuFragmentBinding

class DisciplinesMenu : Fragment() {

    private lateinit var viewModel: DisciplinesMenuViewModel
    private lateinit var binding: DisciplinesMenuFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[DisciplinesMenuViewModel::class.java]

        binding = DisciplinesMenuFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.disciplinesByChoiceBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuDirections.actionDisciplinesMenuToDisciplineByChoiceFragment(
                    viewModel.groupNumberInfo.value!!
                )
            )
        }
        binding.mobilityModuleBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuDirections.actionDisciplinesMenuToMobilityModuleFragment(
                    viewModel.groupNumberInfo.value!!
                )
            )
        }
        binding.electivesBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuDirections.actionDisciplinesMenuToElectives(
                    viewModel.groupNumberInfo.value!!
                )
            )
        }

        binding.groupNumberET.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            viewModel.isValidGroupNumber.value = false
            viewModel.error.value = null
            viewModel.groupNumberInfo.value = null
        })
        binding.groupNumberET.setOnEditorActionListener { _, actionId, event ->
            if (event?.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                submitGroupNumber()
            }
            false
        }

        binding.submitBtn.setOnClickListener {
            submitGroupNumber()
        }
        return binding.root
    }

    private fun submitGroupNumber() {
        val gNumber = binding.groupNumberET.text.toString().trim()
        viewModel.submitGroupNumber(gNumber)
        if (viewModel.isValidGroupNumber.value == true)
            hideKeyboard()
    }

    private fun hideKeyboard() {
        val manager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(requireView().windowToken, 0)
        requireView().clearFocus()
    }
}