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
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.databinding.ElectiveListBinding
import com.example.disciplines.di.SubcomponentNotInitialized
import com.example.disciplines.presentation.model.GroupNumberInfo
import com.example.disciplines.presentation.util.applyGravity
import com.example.disciplines.presentation.util.createToast
import com.example.disciplines.presentation.util.setElectives
import javax.inject.Inject

class ElectivesFragment : Fragment(R.layout.elective_list) {
    private val binding by viewBinding(ElectiveListBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ElectivesViewModel by viewModels { viewModelFactory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = (context.applicationContext as DisciplinesApplication).viewModelsComponent
        component?.inject(this) ?: throw SubcomponentNotInitialized()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.requestStatus.observe(viewLifecycleOwner) {
            it ?: return@observe
            when (it) {
                RequestStatus.LOADING -> onLoading()
                RequestStatus.DONE -> onDone()
                RequestStatus.ERROR -> onError()
            }
        }

        viewModel.electivesList.observe(viewLifecycleOwner) {
            binding.disciplinesList.setElectives(it)
            binding.confirmBtn.isVisible = !it.isNullOrEmpty()
        }

        viewModel.degreeArgs.observe(viewLifecycleOwner, this::setInstructions)

        binding.confirmBtn.setOnClickListener { viewModel.onConfirm() }
        viewModel.navigationEvent.observe(viewLifecycleOwner) { selected ->
            selected ?: return@observe

            if (selected.isNotEmpty())
                navigateToConfirm(selected)
            else {
                showErrorToast()
            }
        }
    }

    private fun setInstructions(degreeArgs: DegreeArgs?) {
        if (degreeArgs == null)
            binding.instructions.setText(R.string.instructions_electives_empty)
        else {
            val studentType = getString(degreeArgs.nameRes)
            val instructionsText =
                getString(R.string.instructions_electives, studentType, degreeArgs.neededPeople)
            binding.instructions.text = SpannableString(instructionsText).apply {
                setSpan(
                    RelativeSizeSpan(0.8f),
                    instructionsText.indexOf('\n') + 1,
                    instructionsText.length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
        }
    }


    private fun showErrorToast() {
        createToast(R.string.toast_text_electives, Toast.LENGTH_LONG)
            .applyGravity(Gravity.CENTER, 0, 0)
            .show()
    }

    private fun navigateToConfirm(selected: List<Discipline.Elective>) {
        findNavController().navigate(
            ElectivesFragmentDirections.actionElectivesToConfirmationFragment(
                SelectedDisciplines.Electives(selected)
            )
        )
        viewModel.navigationFinished()
    }

    private fun onLoading() {
        with(binding) {
            //hide
            instructions.isVisible = false
            confirmBtn.isVisible = false
            //show
            statusImage.isVisible = true
            statusImage.setImageResource(R.drawable.loading_animation)
        }
    }

    private fun onDone() {
        with(binding) {
            //hide
            statusImage.isVisible = false
            //show
            instructions.isVisible = true
        }
    }

    private fun onError() {
        with(binding) {
            //hide
            confirmBtn.isVisible = false
            //show
            instructions.isVisible = true
            instructions.setText(R.string.instructions_error_loading)
            statusImage.isVisible = true
            statusImage.setImageResource(R.drawable.ic_connection_error)
        }
    }
}