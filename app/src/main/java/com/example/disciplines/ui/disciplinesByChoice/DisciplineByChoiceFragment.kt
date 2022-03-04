package com.example.disciplines.ui.disciplinesByChoice

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disciplines.R
import com.example.disciplines.data.database.DisciplinesPair
import com.example.disciplines.data.database.TestValues
import com.example.disciplines.databinding.DisciplinesByChoiceListFragmentBinding

/**
 * A fragment representing a list of Items.
 */
class DisciplineByChoiceFragment : Fragment() {
    private lateinit var binding: DisciplinesByChoiceListFragmentBinding
    private lateinit var list: List<DisciplinesPair>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DisciplinesByChoiceListFragmentBinding.inflate(inflater)

        // Set the adapter
        list = TestValues.generateDisciplinesByChoice(15)
        binding.disciplinesPairsList.adapter =
            DisciplinesByChoiceAdapter(
                list,
                getListHeader(list),
                getButtonListener()
            )
        return binding.root
    }

    private fun getListHeader(list: List<DisciplinesPair>) = resources.getString(
        when (list.isEmpty()) {
            true -> R.string.empty_list_instructions_text
            false -> R.string.instructions_text
        }
    )

    private fun getButtonListener() = View.OnClickListener {
        val checked = list.count { it.checked != DisciplinesPair.Checked.None }
        val text =
            if (checked == list.size)
                "Все дисциплины выбраны! Идем на следующий экран!"
            else
                "Вы должны выборать в каждой паре по одной дисциплине! Выбрано: $checked из ${list.size}"
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

}