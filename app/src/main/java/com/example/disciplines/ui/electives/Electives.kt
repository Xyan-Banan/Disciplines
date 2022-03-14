package com.example.disciplines.ui.electives

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.disciplines.R
import com.example.disciplines.data.network.TestValues
import com.example.disciplines.data.network.model.Elective
import com.example.disciplines.databinding.ListFragmentBinding
import com.example.disciplines.ui.listUtils.Header

class Electives : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private lateinit var viewModel: ElectivesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val list = TestValues.generateElectives(25)

        binding = ListFragmentBinding.inflate(inflater)
        binding.rvList.adapter = ElectivesAdapter(
            list,
            getHeader(list),
            getButtonListener(list)
        )

        viewModel = ElectivesViewModel()
        return binding.root
    }

    private fun getButtonListener(list: List<Elective>) = View.OnClickListener {
        val checked = list.count { it.checked }
        val text =
            if (checked > 0)
                "Идем на следующий экран!"
            else
                "Выберите хотя бы одну дисциплину"
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER,0,0)
        toast.show()
    }

    private fun getHeader(list: List<Elective>) =
        Header(
            getString(R.string.electives),
            getInstructions(list)
        )

    private fun getInstructions(list: List<Elective>) = getString(
        when (list.isEmpty()) {
            true -> R.string.instructions_electives_empty
            false -> R.string.instructions_electives
        }
    )
}