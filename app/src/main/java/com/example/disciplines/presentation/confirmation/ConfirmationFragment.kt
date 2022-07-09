package com.example.disciplines.presentation.confirmation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.disciplines.R
import com.example.disciplines.databinding.ConfirmationFragmentBinding

class ConfirmationFragment : Fragment() {
    private val binding by viewBinding(ConfirmationFragmentBinding::bind)
    private lateinit var viewModel: ConfirmationViewModel
    private val createFileAction =
        registerForActivityResult(ActivityResultContracts.CreateDocument("application/pdf")) { uri ->
            uri ?: return@registerForActivityResult
            viewModel.pdf.inputStream().use { input ->
                requireContext().contentResolver.openOutputStream(uri).use { output ->
                    output ?: error("Provider crushed")
                    input.copyTo(output)
                }
            }
        }

    private lateinit var shareIntent: Intent
    private lateinit var openIntent: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val (selected, groupInfo) = ConfirmationFragmentArgs.fromBundle(requireArguments())
            .run { selected to groupInfo }
        viewModel = ViewModelProvider(
            this,
            ConfirmationViewModelFactory(
                selected,
                groupInfo,
                requireActivity().application
            )
        ).get(ConfirmationViewModel::class.java)

        return inflater.inflate(R.layout.confirmation_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {
            with(viewModel) {
                textView.setText(title)
                selectedListTV.text = selectedText
                isLoading.observe(viewLifecycleOwner) {
                    it?.let { isLoading ->
                        progressBarCreationPdf.isVisible = isLoading
                        fileIconIV.isVisible = !isLoading
                        openBtn.isEnabled = !isLoading
                        saveToPhoneBtn.isEnabled = !isLoading
                        shareBtn.isEnabled = !isLoading
                    }
                }
            }
        }

        viewModel.pdfCreatedEvent.observe(viewLifecycleOwner) { isCreated ->
            if (isCreated) {
                Toast.makeText(
                    context,
                    getString(R.string.file_created),
                    Toast.LENGTH_SHORT
                ).show()
                shareIntent = getShareIntent()
                openIntent = getOpenIntent()
                requireActivity().packageManager.let { pm ->
                    if (shareIntent.resolveActivity(pm) == null) {
                        binding.shareBtn.isEnabled = false
                        binding.shareBtn.error = getString(R.string.can_not_share_file)
                    }
                    if (openIntent.resolveActivity(pm) == null) {
                        binding.openBtn.isEnabled = false
                        binding.shareBtn.error = getString(R.string.no_application_to_open_pdf)
                    }
                }
            }
        }

        binding.openBtn.setOnClickListener {
            startActivity(openIntent)
        }

        binding.saveToPhoneBtn.setOnClickListener {
            createFileAction.launch(viewModel.applicationName)
        }

        binding.shareBtn.setOnClickListener {
            startActivity(shareIntent)
        }
    }

    private fun getOpenIntent(): Intent {
        val uri = viewModel.pdfUri
        return Intent(Intent.ACTION_VIEW)
            .setDataAndType(uri, "application/pdf")
            .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    private fun getShareIntent(): Intent {
        val shareIntent = ShareCompat.IntentBuilder(requireContext())
            .setType("application/pdf")
            .setStream(viewModel.pdfUri)
            .intent
        return Intent.createChooser(shareIntent, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deletePdf()
    }
}