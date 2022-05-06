package com.example.disciplines.ui.electives

import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.disciplines.R
import com.example.disciplines.applyGravity
import com.example.disciplines.data.network.model.Discipline
import com.example.disciplines.databinding.ListFragmentBinding
import com.example.disciplines.ui.CurrentGroup
import com.example.disciplines.ui.listUtils.Header

class ElectivesFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private val viewModel: ElectivesViewModel by viewModels {
        ElectivesViewModelFactory(
            requireActivity().application,
            getButtonListener()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = ListFragmentBinding.inflate(inflater)
//        binding.rvList.adapter = viewModel.adapter.value
//        viewModel.adapter.observe(viewLifecycleOwner) {
//            binding.rvList.adapter = it
//        }
        viewModel.electivesList.observe(viewLifecycleOwner) {
            if (it == null) return@observe
            binding.rvList.adapter = ElectivesAdapter(
                it,
                getHeader(it),
                getButtonListener()
            )
        }

        return binding.root
    }

    private fun getButtonListener() = View.OnClickListener {
        val checked: Array<Discipline> =
            viewModel.electivesList.value!!.filter { it.isChecked }.toTypedArray()

        if (checked.isNotEmpty())
            findNavController().navigate(
                ElectivesFragmentDirections.actionElectivesToConfirmationFragment(checked)
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

    private fun getHeader(list: List<Discipline.Elective>) =
        Header(
            getString(R.string.electives),
            getInstructions(list)
        )

    private fun getInstructions(list: List<Discipline.Elective>) =
        if (list.isEmpty())
            getString(R.string.instructions_electives_empty)
        else {
            val groupName = CurrentGroup.value ?: "353090490010"
            val isBachelor = groupName[2].digitToInt() == 3
            val (studentType, neededPeople) = if (isBachelor) "бакалавриата" to 18 else "магистратуры" to 12
            val spannable = SpannableString(
                getString(
                    R.string.instructions_electives,
                    studentType,
                    neededPeople
                )
            )
            spannable.setSpan(
                RelativeSizeSpan(0.8f),
                spannable.indexOf('.'),
                spannable.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
            spannable
        }
}