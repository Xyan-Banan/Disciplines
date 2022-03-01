package com.example.disciplines.ui.disciplinesByChoice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disciplines.R
import com.example.disciplines.data.database.DisciplinesPair
import com.example.disciplines.data.database.TestValues
import com.example.disciplines.databinding.DisciplinesByChoiceListFragmentBinding

/**
 * A fragment representing a list of Items.
 */
class DisciplineByChoiceFragment : Fragment() {
    private lateinit var binding: DisciplinesByChoiceListFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DisciplinesByChoiceListFragmentBinding.inflate(inflater)

//        binding.disciplinesPairsList.layoutManager = LinearLayoutManager(context)
        // Set the adapter
        val list = TestValues.generateDisciplinesByChoice(10)
        binding.disciplinesPairsList.adapter =
            DisciplinesByChoiceAdapter(
//                    PlaceholderContent.ITEMS
//                emptyList()
                list,
                getListHeader(list)
            )


        return binding.root
    }

    private fun getListHeader(list: List<DisciplinesPair>) = resources.getString(
        when(list.isEmpty()){
            true -> R.string.empty_list_instructions_text
            false -> R.string.instructions_text
        }
    )

}