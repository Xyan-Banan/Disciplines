package com.example.disciplines.presentation.lists.disciplinesByChoice

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.example.disciplines.databinding.DisciplineListBinding
import com.example.disciplines.presentation.GroupNumberInfo
import com.example.disciplines.presentation.util.applyGravity
import com.example.disciplines.presentation.util.setDisciplines
import javax.inject.Inject

class DisciplineByChoiceFragment : Fragment(R.layout.discipline_list) {
    private val binding by viewBinding(DisciplineListBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: DisciplinesByChoiceViewModel by viewModels { viewModelFactory }

    private lateinit var groupInfo: GroupNumberInfo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = (context.applicationContext as DisciplinesApplication).component
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        groupInfo = DisciplineByChoiceFragmentArgs.fromBundle(requireArguments()).groupInfo
        viewModel.getDisciplines(groupInfo.groupNumber)

        viewModel.requestStatus.observe(viewLifecycleOwner) {
            it ?: return@observe
            when (it) {
                RequestStatus.LOADING -> binding.onLoading()
                RequestStatus.DONE -> binding.onDone()
                RequestStatus.ERROR -> binding.onError()
            }
        }

        viewModel.disciplinesList.observe(viewLifecycleOwner) {
            binding.disciplinesList.setDisciplines(it)
        }

        binding.confirmBtn.setOnClickListener { onConfirm() }
    }

    private fun DisciplineListBinding.onLoading() {
        //hide
        instructions.isVisible = false
        confirmBtn.isVisible = false
        //show
        statusImage.isVisible = true
        statusImage.setImageResource(R.drawable.loading_animation)
    }

    private fun DisciplineListBinding.onDone() {
        //hide
        statusImage.isVisible = false
        //show
        instructions.isVisible = true

        if (viewModel.disciplinesList.value.isNullOrEmpty()) {
            instructions.setText(R.string.instructions_disciplinesByChoice_empty)
            confirmBtn.isVisible = false
        } else {
            instructions.setText(R.string.instructions_disciplinesByChoice)
            confirmBtn.isVisible = true
        }
    }

    private fun DisciplineListBinding.onError() {
        //hide
        confirmBtn.isVisible = false
        //show
        instructions.isVisible = true
        instructions.setText(R.string.instructions_error_loading)
        statusImage.isVisible = true
        statusImage.setImageResource(R.drawable.ic_connection_error)
    }

    private fun onConfirm() {
        if (viewModel.disciplinesList.value.isNullOrEmpty()) return

        if (viewModel.isCanNavigate) {
            findNavController().navigate(
                DisciplineByChoiceFragmentDirections.actionDisciplineByChoiceFragmentToConfirmationFragment(
                    SelectedDisciplines.ByChoice(viewModel.disciplinesList.value!!), groupInfo
                )
            )
        } else {
            val text = getString(
                R.string.toast_text_disciplinesByChoice,
                viewModel.checked,
                viewModel.disciplinesList.value!!.size
            )
            Toast.makeText(context, text, Toast.LENGTH_LONG)
                .applyGravity(Gravity.CENTER, 0, 0)
                .show()
        }
    }
}