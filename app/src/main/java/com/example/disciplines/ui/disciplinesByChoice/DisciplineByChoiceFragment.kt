package com.example.disciplines.ui.disciplinesByChoice

import android.os.Bundle
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
import com.example.disciplines.databinding.DisciplineListBinding

class DisciplineByChoiceFragment : Fragment() {
    private lateinit var binding: DisciplineListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val viewModel: DisciplinesByChoiceViewModel by viewModels()

        // TODO: refactor to simple scroll view
        binding = DisciplineListBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.confirmBtn.setOnClickListener {
            if (viewModel.disciplinesList.value.isNullOrEmpty()) return@setOnClickListener

            val list = viewModel.disciplinesList.value!!

            val checked = list.count { it.checkedIndex >= 0 }

            if (checked == list.size) {
                println("Все дисциплины выбраны! Идем на следующий экран!")
                val checkedItems: Array<Discipline> =
                    list.map { it.list[it.checkedIndex] }.toTypedArray()
                findNavController().navigate(
                    DisciplineByChoiceFragmentDirections.actionDisciplineByChoiceFragmentToConfirmationFragment(
                        checkedItems
                    )
                )
            } else {
                val text = getString(R.string.toast_text_disciplinesByChoice, checked, list.size)
                Toast.makeText(context, text, Toast.LENGTH_LONG)
                    .applyGravity(Gravity.CENTER, 0, 0)
                    .show()
            }
        }

        return binding.root
    }
}