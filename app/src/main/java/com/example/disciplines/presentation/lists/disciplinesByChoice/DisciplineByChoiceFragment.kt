package com.example.disciplines.presentation.lists.disciplinesByChoice

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.disciplines.DisciplinesApplication
import com.example.disciplines.R
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.databinding.DisciplineListBinding
import com.example.disciplines.presentation.GroupNumberInfo
import com.example.disciplines.presentation.util.applyGravity
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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.confirmBtn.setOnClickListener { onConfirm() }
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