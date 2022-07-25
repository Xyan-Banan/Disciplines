package com.example.disciplines.presentation.confirmation

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ShareCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.disciplines.DisciplinesApplication
import com.example.disciplines.R
import com.example.disciplines.data.models.SelectedDisciplines
import com.example.disciplines.databinding.ConfirmationFragmentBinding
import com.example.disciplines.presentation.model.GroupNumberInfo
import com.example.disciplines.presentation.util.showToast
import javax.inject.Inject

class ConfirmationFragment : Fragment(R.layout.confirmation_fragment) {
    private val binding by viewBinding(ConfirmationFragmentBinding::bind)

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

    private lateinit var selected: SelectedDisciplines
    private lateinit var groupInfo: GroupNumberInfo

    @Inject
    lateinit var viewModelFactory: ConfirmationViewModelFactory.Factory
    private val viewModel: ConfirmationViewModel by viewModels {
        viewModelFactory.create(selected)
    }

    override fun onAttach(context: Context) {
        selected = ConfirmationFragmentArgs.fromBundle(requireArguments()).selected
        groupInfo = ConfirmationFragmentArgs.fromBundle(requireArguments()).groupInfo
        val component = (context.applicationContext as DisciplinesApplication).confirmationComponent
        component.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.textView.setText(viewModel.title)
        binding.selectedListTV.text = viewModel.selectedText

        viewModel.isLoading.observe(viewLifecycleOwner) {
            it?.let(this::setUiState)
        }

        viewModel.pdfCreatedEvent.observe(viewLifecycleOwner) { isCreated ->
            if (isCreated) {
                showToast(R.string.file_created)
                shareIntent = getShareIntent(viewModel.pdfUri)
                openIntent = getOpenIntent(viewModel.pdfUri)
                checkIntentsResolving()
            }
        }

        setUpNavigationButtons()
    }

    private fun setUiState(isLoading: Boolean) {
        with(binding) {
            progressBarCreationPdf.isVisible = isLoading
            fileIconIV.isVisible = !isLoading
            openBtn.isEnabled = !isLoading
            saveToPhoneBtn.isEnabled = !isLoading
            shareBtn.isEnabled = !isLoading
        }
    }

    private fun checkIntentsResolving() {
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

    private fun setUpNavigationButtons() {
        binding.openBtn.setOnClickListener { startActivity(openIntent) }

        binding.saveToPhoneBtn.setOnClickListener { createFileAction.launch(viewModel.applicationName) }

        binding.shareBtn.setOnClickListener { startActivity(shareIntent) }
    }

    private fun getOpenIntent(uri: Uri): Intent {
        return Intent(Intent.ACTION_VIEW)
            .setDataAndType(uri, "application/pdf")
            .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    private fun getShareIntent(uri: Uri): Intent {
        val shareIntent = ShareCompat.IntentBuilder(requireContext())
            .setType("application/pdf")
            .setStream(uri)
            .intent
        return Intent.createChooser(shareIntent, null)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.deletePdf()
    }
}