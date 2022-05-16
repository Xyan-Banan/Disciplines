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

    val openFileAction = registerForActivityResult(ActivityResultContracts.OpenDocument()) {}

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

        viewModel.pdfCreatedEvent.observe(viewLifecycleOwner) { isCreated ->
            if (isCreated) Toast.makeText(context, "Файл создан", Toast.LENGTH_SHORT).show()
        }

        val binding = ConfirmationFragmentBinding.inflate(inflater)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        binding.openBtn.setOnClickListener {
            startActivity(getOpenIntent())
//            startActivity(Intent.createChooser(intent, "Open file"))
        }

        binding.saveToPhoneBtn.setOnClickListener {
            createFileAction.launch(viewModel.applicationName)
        }

        binding.shareBtn.setOnClickListener{
            startActivity(getShareIntent())
        }

        return binding.root
    }

    private fun getOpenIntent(): Intent {
//            val uri = Uri.fromFile(viewModel.pdf)
        val uri = viewModel.pdfUri
        return Intent(Intent.ACTION_VIEW)
            .setDataAndType(uri, "application/pdf")
            .setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }

    private fun getShareIntent(): Intent {
        val uri = viewModel.pdfUri
//        val intent = Intent(Intent.ACTION_SEND)
//            .addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//            .setType("*/*")
//            .putExtra(Intent.EXTRA_STREAM, uri)
//            .setDataAndType(uri, "*/*")
//            .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        val shareIntent = ShareCompat.IntentBuilder(requireContext())
            .setType("*/*")
            .setStream(uri)
            .intent
        return Intent.createChooser(shareIntent,null)
    }
}