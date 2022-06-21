package com.example.disciplines.ui.lists.disciplinesByChoice

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.disciplines.GroupNumberInfo
import com.example.disciplines.R
import com.example.disciplines.applyGravity
import com.example.disciplines.data.network.model.SelectedDisciplines
import com.example.disciplines.databinding.DisciplineListBinding

class DisciplineByChoiceFragment : Fragment() {
    private lateinit var binding: DisciplineListBinding
    private lateinit var viewModel: DisciplinesByChoiceViewModel
    private lateinit var groupInfo: GroupNumberInfo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        groupInfo = DisciplineByChoiceFragmentArgs.fromBundle(requireArguments()).groupInfo
        viewModel =
            ViewModelProvider(
                this,
                DisciplinesByChoiceViewModelFactory(groupInfo.groupNumber)
            ).get(DisciplinesByChoiceViewModel::class.java)

        binding = DisciplineListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.confirmBtn.setOnClickListener {
            if (viewModel.disciplinesList.value.isNullOrEmpty()) return@setOnClickListener

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
}