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
import com.example.disciplines.R
import com.example.disciplines.data.network.TestValues
import com.example.disciplines.data.network.model.DisciplineS
import com.example.disciplines.databinding.ListFragmentBinding
import com.example.disciplines.ui.CurrentGroup
import com.example.disciplines.ui.listUtils.Header

class ElectivesFragment : Fragment() {
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

    private fun getButtonListener(list: List<DisciplineS.Elective>) = View.OnClickListener {
        val checked = list.count { it.isChecked }
        val text =
            if (checked > 0)
                "Идем на следующий экран!"
            else
                "Выберите хотя бы одну дисциплину"
        val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
        toast.setGravity(Gravity.CENTER, 0, 0)
        toast.show()
    }

    private fun getHeader(list: List<DisciplineS.Elective>) =
        Header(
            getString(R.string.electives),
            getInstructions(list)
        )

    private fun getInstructions(list: List<DisciplineS.Elective>) =
        if (list.isEmpty())
            getString(R.string.instructions_electives_empty)
        else {
            val groupName = CurrentGroup.value
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