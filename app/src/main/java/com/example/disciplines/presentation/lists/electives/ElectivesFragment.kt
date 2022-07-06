package com.example.disciplines.presentation.lists.electives

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.disciplines.DisciplinesApplication
import com.example.disciplines.GroupNumberInfo
import com.example.disciplines.R
import com.example.disciplines.applyGravity
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.data.source.network.RequestStatus
import com.example.disciplines.databinding.ElectiveListBinding
import javax.inject.Inject

class ElectivesFragment : Fragment() {
    lateinit var binding: ElectiveListBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: ElectivesViewModel by viewModels { viewModelFactory }

    lateinit var groupInfo: GroupNumberInfo
    override fun onAttach(context: Context) {
        super.onAttach(context)
        val component = (context.applicationContext as DisciplinesApplication).component
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        groupInfo = ElectivesFragmentArgs.fromBundle(requireArguments()).groupInfo
        viewModel.getElectivesList(groupInfo.groupNumber)
        binding = ElectiveListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.confirmBtn.setOnClickListener { onConfirm() }

        viewModel.requestStatus.observe(viewLifecycleOwner, ::setInstructions)
    }

    private fun setInstructions(requestStatus: RequestStatus?) {
        binding.instructions.text = requestStatus?.let {
            viewModel.getInstructions(resources, groupInfo)
        }
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