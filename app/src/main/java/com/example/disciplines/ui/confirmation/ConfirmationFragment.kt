package com.example.disciplines.ui.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.databinding.ConfirmationFragmentBinding

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
        viewModel = ViewModelProvider(
            this,
            ConfirmationViewModelFactory(
                selected,
                requireActivity().application
            )
        ).get(ConfirmationViewModel::class.java)

        viewModel.pdfCreatedEvent.observe(viewLifecycleOwner) { isCreated ->
            if(isCreated) Toast.makeText(context,"Файл создан",Toast.LENGTH_SHORT).show()
        }

        val binding = ConfirmationFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.openBtn.setOnClickListener {

        }

        binding.saveToPhoneBtn.setOnClickListener {
            createFileAction.launch(viewModel.applicationName)
        }

        return binding.root
    }
}