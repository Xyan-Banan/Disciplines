package com.example.disciplines.presentation.lists.mobilityModule

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
import com.example.disciplines.data.models.Discipline
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.databinding.MobilityModuleListBinding
import com.example.disciplines.presentation.lists.mobilityModule.NavigationEvent.Can
import com.example.disciplines.presentation.lists.mobilityModule.NavigationEvent.Not
import com.example.disciplines.presentation.model.GroupNumberInfo
import com.example.disciplines.presentation.util.applyGravity
import com.example.disciplines.presentation.util.setMobilityModules
import javax.inject.Inject

class MobilityModuleFragment : Fragment(R.layout.mobility_module_list) {
    private val binding by viewBinding(MobilityModuleListBinding::bind)

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MobilityModuleViewModel by viewModels { viewModelFactory }

    private lateinit var groupInfo: GroupNumberInfo

    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = (context.applicationContext as DisciplinesApplication).component
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        groupInfo = MobilityModuleFragmentArgs.fromBundle(requireArguments()).groupInfo
        viewModel.getModulesList(groupInfo.groupNumber)

        viewModel.requestStatus.observe(viewLifecycleOwner) {
            it ?: return@observe
            when (it) {
                RequestStatus.LOADING -> onLoading(binding)
                RequestStatus.DONE -> onDone(binding)
                RequestStatus.ERROR -> onError(binding)
            }
        }

        viewModel.modulesList.observe(viewLifecycleOwner) {
            binding.radioGroup2.setMobilityModules(it)
            binding.confirmBtn.isVisible = !it.isNullOrEmpty()
            binding.instructions.setText(
                if (it.isNullOrEmpty()) R.string.instructions_mobilityModule_empty
                else R.string.instructions_mobilityModule
            )
        }

        binding.confirmBtn.setOnClickListener { viewModel.onConfirm(binding.radioGroup2) }
        viewModel.navigationEvent.observe(viewLifecycleOwner) {
            it?: return@observe
            when(it){
                is Can -> navigateToConfirm(it.module)
                is Not -> showErrorToast()
            }
        }
    }

    private fun onLoading(binding: MobilityModuleListBinding) {
        with(binding) {
            //hide
            instructions.isVisible = false
            confirmBtn.isVisible = false
            //show
            statusImage.isVisible = true
            statusImage.setImageResource(R.drawable.loading_animation)
        }
    }

    private fun onDone(binding: MobilityModuleListBinding) {
        with(binding) {
            //hide
            statusImage.isVisible = false
            //show
            instructions.isVisible = true
        }
    }

    private fun onError(binding: MobilityModuleListBinding) {
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

    private fun navigateToConfirm(module: Discipline.MobilityModule) {
        findNavController().navigate(
            MobilityModuleFragmentDirections.actionMobilityModuleFragmentToConfirmationFragment(
                SelectedDisciplines.MobilityModule(module), groupInfo
            )
        )
        viewModel.navigationFinished()
    }

    private fun showErrorToast() {
        val text = getString(R.string.toast_text_mobilityModule)
        Toast.makeText(context, text, Toast.LENGTH_LONG)
            .applyGravity(Gravity.CENTER)
            .show()
    }
}