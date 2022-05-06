package com.example.disciplines.ui.confirmation

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.disciplines.R
import com.example.disciplines.databinding.ConfirmationFragmentBinding
import java.text.DateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class ConfirmationFragment : Fragment() {
    private lateinit var viewModel: ConfirmationViewModel
    private val contract = object : ActivityResultContracts.CreateDocument() {
        override fun createIntent(context: Context, input: String): Intent {
            return super.createIntent(context, input).setType("application/pdf")
        }
    }
    private val createFileAction = registerForActivityResult(contract) {
        viewModel.pdf.inputStream().use { input ->
            requireContext().contentResolver.openOutputStream(it).use { output ->
                output ?: throw IllegalStateException()
                input.copyTo(output)
            }
        }
//        TODO: delete cached file and use saved or clear cache folder on dispose
//        viewModel.pdf.delete()
//        viewModel.pdf = requireContext().contentResolver.openFile(it,"w", CancellationSignal()).fileDescriptor.f
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val selected = ConfirmationFragmentArgs.fromBundle(requireArguments()).selected
        val previousFragmentId = findNavController().previousBackStackEntry?.destination?.id as Int
        viewModel = ViewModelProvider(
            this,
            ConfirmationViewModelFactory(
                selected,
                previousFragmentId,
                requireActivity().application
            )
        ).get(ConfirmationViewModel::class.java)

        val text = when (previousFragmentId) {
            R.id.disciplineByChoiceFragment -> "From BY CHOICE"
            R.id.mobilityModuleFragment -> "From MOBILITY"
            R.id.electivesFragment -> "From ELECTIVE"
            else -> "error"
        }

        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()

        val binding = ConfirmationFragmentBinding.inflate(inflater)
        binding.viewModel = viewModel
        binding.openBtn.setOnClickListener {

        }
        binding.saveToPhoneBtn.setOnClickListener {
            val dateTime =
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    DateTimeFormatter.ofPattern("dd.MM.yyyy_hh.mm.ss").format(LocalDateTime.now())
                } else {
                    DateFormat.getDateTimeInstance(
                        DateFormat.SHORT,
                        DateFormat.MEDIUM,
                        Locale.getDefault()
                    ).format(Date())
                }
            createFileAction.launch("Заявление $dateTime")
        }

        return binding.root
    }
}