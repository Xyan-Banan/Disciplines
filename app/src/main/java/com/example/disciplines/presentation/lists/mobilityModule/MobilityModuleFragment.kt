package com.example.disciplines.presentation.lists.mobilityModule

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
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
import com.example.disciplines.databinding.MobilityModuleListBinding
import javax.inject.Inject

class MobilityModuleFragment : Fragment() {
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

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.confirmBtn.setOnClickListener { onConfirm() }
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