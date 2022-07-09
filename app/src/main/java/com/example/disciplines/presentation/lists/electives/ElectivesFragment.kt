package com.example.disciplines.presentation.lists.electives

import android.content.Context
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.disciplines.DisciplinesApplication
import com.example.disciplines.R
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.databinding.ElectiveListBinding
import com.example.disciplines.presentation.GroupNumberInfo
import com.example.disciplines.presentation.util.applyGravity
import com.example.disciplines.presentation.util.setDisciplines
import com.example.disciplines.presentation.util.setElectives
import javax.inject.Inject

class ElectivesFragment : Fragment(R.layout.elective_list) {
    private val binding by viewBinding(ElectiveListBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ElectivesViewModel by viewModels { viewModelFactory }

    lateinit var groupInfo: GroupNumberInfo
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = (context.applicationContext as DisciplinesApplication).component
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        groupInfo = ElectivesFragmentArgs.fromBundle(requireArguments()).groupInfo
        viewModel.getElectivesList(groupInfo.groupNumber)

        viewModel.requestStatus.observe(viewLifecycleOwner) {
            it ?: return@observe
            when (it) {
                RequestStatus.LOADING -> binding.onLoading()
                RequestStatus.DONE -> binding.onDone()
                RequestStatus.ERROR -> binding.onError()
            }
        }

        viewModel.electivesList.observe(viewLifecycleOwner) {
            binding.disciplinesList.setElectives(it)
        }

        binding.confirmBtn.setOnClickListener { onConfirm() }
    }

    private fun ElectiveListBinding.onLoading() {
        //hide
        instructions.isVisible = false
        confirmBtn.isVisible = false
        //show
        statusImage.isVisible = true
        statusImage.setImageResource(R.drawable.loading_animation)
    }

    private fun ElectiveListBinding.onDone() {
        //hide
        statusImage.isVisible = false
        //show
        instructions.isVisible = true

        if (viewModel.electivesList.value.isNullOrEmpty()) {
            instructions.setText(R.string.instructions_electives_empty)
            confirmBtn.isVisible = false
        } else {
            val (studentType, neededPeople) = viewModel.getDegreeArgs(groupInfo)
            val instructionsText =
                getString(R.string.instructions_electives, studentType, neededPeople)
            instructions.text = SpannableString(instructionsText).apply {
                setSpan(
                    RelativeSizeSpan(0.8f),
                    instructionsText.indexOf('\n') + 1,
                    instructionsText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            confirmBtn.isVisible = true
        }
    }

    private fun ElectiveListBinding.onError() {
        //hide
        confirmBtn.isVisible = false
        //show
        instructions.isVisible = true
        instructions.setText(R.string.instructions_error_loading)
        statusImage.isVisible = true
        statusImage.setImageResource(R.drawable.ic_connection_error)
    }

    private fun onConfirm() {
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
}