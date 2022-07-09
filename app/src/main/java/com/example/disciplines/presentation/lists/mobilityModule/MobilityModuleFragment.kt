package com.example.disciplines.presentation.lists.mobilityModule

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.disciplines.DisciplinesApplication
import com.example.disciplines.presentation.GroupNumberInfo
import com.example.disciplines.R
import com.example.disciplines.presentation.util.applyGravity
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.databinding.DisciplineListBinding
import com.example.disciplines.databinding.MobilityModuleListBinding
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
                RequestStatus.LOADING -> binding.onLoading()
                RequestStatus.DONE -> binding.onDone()
                RequestStatus.ERROR -> binding.onError()
            }
        }

        viewModel.modulesList.observe(viewLifecycleOwner) {
            binding.radioGroup2.setMobilityModules(it)
        }

        binding.confirmBtn.setOnClickListener { onConfirm() }
    }

    private fun MobilityModuleListBinding.onLoading() {
        //hide
        instructions.isVisible = false
        confirmBtn.isVisible = false
        //show
        statusImage.isVisible = true
        statusImage.setImageResource(R.drawable.loading_animation)
    }

    private fun MobilityModuleListBinding.onDone() {
        //hide
        statusImage.isVisible = false
        //show
        instructions.isVisible = true

        if (viewModel.modulesList.value.isNullOrEmpty()) {
            instructions.setText(R.string.instructions_mobilityModule_empty)
            confirmBtn.isVisible = false
        } else {
            instructions.setText(R.string.instructions_mobilityModule)
            confirmBtn.isVisible = true
        }
    }

    private fun MobilityModuleListBinding.onError() {
        //hide
        confirmBtn.isVisible = false
        //show
        instructions.isVisible = true
        instructions.setText(R.string.instructions_error_loading)
        statusImage.isVisible = true
        statusImage.setImageResource(R.drawable.ic_connection_error)
    }

    private fun onConfirm() {
        val checkedId = binding.radioGroup2.checkedRadioButtonId //это именно id, а не индекс!!!

        if (checkedId >= 0) {
            val checkedRadioButton = binding.radioGroup2.findViewById<RadioButton>(checkedId)
            val checkedIndex = binding.radioGroup2.indexOfChild(checkedRadioButton)
            val checked = viewModel.modulesList.value!![checkedIndex]
            findNavController().navigate(
                MobilityModuleFragmentDirections.actionMobilityModuleFragmentToConfirmationFragment(
                    SelectedDisciplines.MobilityModule(checked), groupInfo
                )
            )

        } else {
            val text = getString(R.string.toast_text_mobilityModule)
            Toast.makeText(context, text, Toast.LENGTH_LONG)
                .applyGravity(Gravity.CENTER, 0, 0)
                .show()
        }
    }
}