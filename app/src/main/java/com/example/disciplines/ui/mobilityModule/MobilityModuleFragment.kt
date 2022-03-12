package com.example.disciplines.ui.mobilityModule

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.disciplines.R
import com.example.disciplines.data.network.model.MobilityModule
import com.example.disciplines.data.network.RequestStatus
import com.example.disciplines.databinding.MobilityModuleItemBinding
import com.example.disciplines.databinding.MobilityModuleListBinding

class MobilityModuleFragment : Fragment() {
    private lateinit var binding: MobilityModuleListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
//        val list = TestValues.generateMobilityModules(5)
        val groupName = MobilityModuleFragmentArgs.fromBundle(requireArguments()).groupName
        val viewModel = MobilityModuleViewModel(groupName)
        binding = MobilityModuleListBinding.inflate(inflater)

        viewModel.modulesList.observe(viewLifecycleOwner) {
            it?.let {
                setList(it)
                binding.instructions.text = getInstructions(it)
                binding.confirmBtn.visibility = getButtonVisibility(it)
            }
        }

        viewModel.requestStatus.observe(viewLifecycleOwner) {
            when (it ?: RequestStatus.LOADING) {
                RequestStatus.LOADING -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.loading_animation)
                    binding.instructions.visibility = View.GONE
                    binding.confirmBtn.visibility = View.GONE
                }
                RequestStatus.DONE -> {
                    binding.statusImage.visibility = View.GONE
                    binding.instructions.visibility = View.VISIBLE
                    binding.confirmBtn.visibility = View.VISIBLE
                }
                RequestStatus.ERROR -> {
                    binding.statusImage.visibility = View.VISIBLE
                    binding.statusImage.setImageResource(R.drawable.ic_connection_error)
                }
            }
        }

//        binding.instructions.text = getInstructions(list)
//        binding.confirmBtn.visibility = getButtonVisibility(list)

        binding.confirmBtn.setOnClickListener {
            val checkedId = binding.radioGroup2.checkedRadioButtonId //это именно id, а не индекс!!!
            val text =
                if (checkedId >= 0)
                    "Выбран модуль мобильности $checkedId"
                else
                    "Необходимо выбрать хотя бы один модуль мобильности"
            val toast = Toast.makeText(context, text, Toast.LENGTH_LONG)
            toast.setGravity(Gravity.CENTER, 0, 0)
            toast.show()
        }
        return binding.root
    }

    private fun setList(list: List<MobilityModule>) {
        for (item in list) {
            val btn = MobilityModuleItemBinding.inflate(
                LayoutInflater.from(context),
                binding.radioGroup2,
                false
            )
            btn.mobilityModule = item
//                btn.root.setOnClickListener { Log.i("TAG", "${it.id} $it") }
            binding.radioGroup2.addView(btn.root)
        }
    }

    private fun getInstructions(list: List<MobilityModule>) = resources.getString(
        when (list.isEmpty()) {
            true -> R.string.instructions_mobilityModule_empty
            false -> R.string.instructions_mobilityModule
        }
    )

    private fun getButtonVisibility(list: List<MobilityModule>) =
        if (list.isEmpty()) View.GONE else View.VISIBLE
}