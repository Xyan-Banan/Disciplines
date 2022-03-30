package com.example.disciplines.ui.confirmation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disciplines.R
import com.example.disciplines.data.network.model.Discipline

class ConfirmationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val selected = ConfirmationFragmentArgs.fromBundle(requireArguments()).selected

        val text = when (selected.firstOrNull()) {
            is Discipline.ByChoice -> "From BY CHOICE"
            is Discipline.MobilityModule ->"From MOBILITY"
            is Discipline.Elective ->"From ELECTIVE"
            else -> "error"
        }

        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.confirmation_fragment, container, false)
    }
}