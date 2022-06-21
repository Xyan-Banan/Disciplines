package com.example.disciplines.ui.confirmation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ShareCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.disciplines.R
import com.example.disciplines.databinding.ConfirmationFragmentBinding

class ConfirmationFragment : Fragment() {
    private lateinit var binding: ConfirmationFragmentBinding
    private lateinit var viewModel: ConfirmationViewModel
    private val contract = object : ActivityResultContracts.CreateDocument() {
        override fun createIntent(context: Context, input: String): Intent {
            return super.createIntent(context, input).setType("application/pdf")
        }
    }
    private val createFileAction = registerForActivityResult(contract) {
        it ?: return@registerForActivityResult
        viewModel.pdf.inputStream().use { input ->
            requireContext().contentResolver.openOutputStream(it).use { output ->
                output ?: throw IllegalStateException()
                input.copyTo(output)
            }
        }
    }
//        TODO: delete cached file and use saved or clear cache folder on dispose
//        viewModel.pdf.delete()
//        viewModel.pdf = requireContext().contentResolver.openFile(it,"w", CancellationSignal()).fileDescriptor.f

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

        binding = ConfirmationFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
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
}