package com.example.disciplines.presentation.disciplinesMenu

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.disciplines.R
import com.example.disciplines.databinding.DisciplinesMenuFragmentBinding

class DisciplinesMenuFragment : Fragment(R.layout.disciplines_menu_fragment) {

    private val viewModel: DisciplinesMenuViewModel by viewModels()
    private val binding by viewBinding(DisciplinesMenuFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.disciplinesByChoiceBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuFragmentDirections.actionDisciplinesMenuToDisciplineByChoiceFragment(
                    viewModel.groupNumberInfo.value!!
                )
            )
        }
        binding.mobilityModuleBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuFragmentDirections.actionDisciplinesMenuToMobilityModuleFragment(
                    viewModel.groupNumberInfo.value!!
                )
            )
        }
        binding.electivesBtn.setOnClickListener {
            findNavController().navigate(
                DisciplinesMenuFragmentDirections.actionDisciplinesMenuToElectives(
                    viewModel.groupNumberInfo.value!!
                )
            )
        }

        binding.groupNumberET.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            viewModel.dropGroupInfo()
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

        viewModel.error.observe(viewLifecycleOwner) {
            binding.groupNumberET.error = it?.let { getString(it) }
        }

        viewModel.groupNumberInfo.observe(viewLifecycleOwner) {
            it?.run {
                binding.groupInfoTV.text =
                    getString(R.string.groupInfo, course, semester, admissionYear)
            }
        }

        viewModel.isValidGroupNumber.observe(viewLifecycleOwner) {
            with(binding) {
                disciplinesByChoiceBtn.isEnabled = it
                mobilityModuleBtn.isEnabled = it
                electivesBtn.isEnabled = it
            }
        }

        viewModel.isGroupInfoVisible.observe(viewLifecycleOwner) {
            binding.groupInfoTV.visibility = it
        }
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