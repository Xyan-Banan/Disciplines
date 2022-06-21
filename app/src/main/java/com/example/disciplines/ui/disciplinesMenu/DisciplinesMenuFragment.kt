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
import com.example.disciplines.R
import com.example.disciplines.databinding.DisciplinesMenuFragmentBinding

class DisciplinesMenuFragment : Fragment() {

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

        return binding.root
    }

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
            binding.run {
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