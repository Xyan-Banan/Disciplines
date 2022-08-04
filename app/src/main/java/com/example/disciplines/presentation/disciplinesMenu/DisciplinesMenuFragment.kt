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
import com.example.disciplines.DisciplinesApplication
import com.example.disciplines.R
import com.example.disciplines.databinding.DisciplinesMenuFragmentBinding
import com.example.disciplines.presentation.model.GroupNumberInfo

class DisciplinesMenuFragment : Fragment(R.layout.disciplines_menu_fragment) {

    private val viewModel: DisciplinesMenuViewModel by viewModels()
    private val binding by viewBinding(DisciplinesMenuFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpNavigationButtons()

        binding.groupNumberET.addTextChangedListener(onTextChanged = { _, _, _, _ ->
            viewModel.dropGroupInfo()
        })

        binding.groupNumberET.setOnEditorActionListener { _, actionId, event ->
            if (event?.keyCode == KeyEvent.KEYCODE_ENTER || actionId == EditorInfo.IME_ACTION_DONE) {
                submitGroupNumber()
            }
            false
        }

        binding.submitBtn.setOnClickListener { submitGroupNumber() }

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
            if (it) hideKeyboard()
            with(binding) {
                disciplinesByChoiceBtn.isEnabled = it
                mobilityModuleBtn.isEnabled = it
                electivesBtn.isEnabled = it
            }
        }

        viewModel.isGroupInfoVisible.observe(viewLifecycleOwner, binding.groupInfoTV::setVisibility)
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it?.let {
                submitGroupNumberToApplication(it.groupInfo)
                findNavController().navigate(it.directions)
                viewModel.navigated()
            }
        }

    }

    private fun setUpNavigationButtons() {
        with(DisciplinesMenuFragmentDirections) {
            binding.disciplinesByChoiceBtn.setOnClickListener {
                viewModel.navigate(
                    actionDisciplinesMenuToDisciplineByChoiceFragment()
                )
            }
            binding.mobilityModuleBtn.setOnClickListener {
                viewModel.navigate(
                    actionDisciplinesMenuToMobilityModuleFragment()
                )
            }
            binding.electivesBtn.setOnClickListener {
                viewModel.navigate(
                    actionDisciplinesMenuToElectives()
                )
            }
        }
    }

    private fun submitGroupNumber() {
        val gNumber = binding.groupNumberET.text.toString().trim()
        viewModel.submitGroupNumber(gNumber)
    }

    private fun submitGroupNumberToApplication(groupInfo: GroupNumberInfo) {
        (requireContext().applicationContext as DisciplinesApplication).setGroupInfo(groupInfo)
    }

    private fun hideKeyboard() {
        val manager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        manager.hideSoftInputFromWindow(requireView().windowToken, 0)
        requireView().clearFocus()
    }
}