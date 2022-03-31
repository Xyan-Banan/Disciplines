package com.example.disciplines.ui.confirmation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.disciplines.R

class ConfirmationFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val selected = ConfirmationFragmentArgs.fromBundle(requireArguments()).selected
        val previousFragmentId = findNavController().previousBackStackEntry?.destination?.id
        val text = when(previousFragmentId){
            R.id.disciplineByChoiceFragment -> "From BY CHOICE"
            R.id.mobilityModuleFragment ->"From MOBILITY"
            R.id.electivesFragment ->"From ELECTIVE"
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