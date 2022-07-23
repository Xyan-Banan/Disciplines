package com.example.disciplines.presentation.lists.disciplinesByChoice

import android.content.Context
import android.os.Bundle
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
import com.example.disciplines.data.models.DisciplinesBundle
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.databinding.DisciplineListBinding
import com.example.disciplines.presentation.lists.disciplinesByChoice.NavigationEvent.*
import com.example.disciplines.presentation.model.GroupNumberInfo
import com.example.disciplines.presentation.util.applyGravity
import com.example.disciplines.presentation.util.createToast
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
                RequestStatus.LOADING -> onLoading()
                RequestStatus.DONE -> onDone()
                RequestStatus.ERROR -> onError()
            }
        }

        viewModel.disciplinesList.observe(viewLifecycleOwner) {
            binding.disciplinesList.setDisciplines(it)
            binding.confirmBtn.isVisible = !it.isNullOrEmpty()
            binding.instructions.setText(
                if (it.isNullOrEmpty()) R.string.instructions_disciplinesByChoice_empty
                else R.string.instructions_disciplinesByChoice
            )
        }

        binding.confirmBtn.setOnClickListener { viewModel.onConfirm() }
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            when (it) {
                is Can -> navigateToConfirm(it.list)
                is Not -> showErrorToast(it.checked, it.total)
            }
        }
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

    private fun navigateToConfirm(bundles: List<DisciplinesBundle>) {
        findNavController().navigate(
            DisciplineByChoiceFragmentDirections.actionDisciplineByChoiceFragmentToConfirmationFragment(
                SelectedDisciplines.ByChoice(bundles), groupInfo
            )
        )
        viewModel.navigationFinished()
    }

    private fun showErrorToast(checked: Int, total: Int) {
        val text = getString(
            R.string.toast_text_disciplinesByChoice,
            checked,
            total
        )
        createToast(text, Toast.LENGTH_LONG)
            .applyGravity(Gravity.CENTER)
            .show()
    }
}