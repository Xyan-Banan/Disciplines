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
            val text =
                if (checked == list.size)
                    "Все дисциплины выбраны! Идем на следующий экран!"
                else
                    "Вы должны выборать в каждой паре по одной дисциплине! Выбрано: $checked из ${list.size}"
            val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()

            if (checked == list.size) {
                val checkedItems: Array<Discipline> =
                    list.map { it.list[it.checkedIndex] }.toTypedArray()
                findNavController().navigate(
                    DisciplineByChoiceFragmentDirections.actionDisciplineByChoiceFragmentToConfirmationFragment(
                        checkedItems
                    )
                )
            }
        }
        return binding.root
    }
}