package com.example.disciplines.ui.mobilityModule

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.disciplines.data.network.model.Discipline.MobilityModule
import com.example.disciplines.databinding.MobilityModuleItemBinding
import com.example.disciplines.ui.listUtils.AbstractListAdapter
import com.example.disciplines.ui.listUtils.Header
import com.example.disciplines.ui.listUtils.ViewHolder

class MobilityModuleAdapter(
    items: List<MobilityModule>,
    header: Header,
    buttonListener: View.OnClickListener
) :
    AbstractListAdapter<MobilityModule>(items, header, buttonListener) {

    class MobilityModuleViewHolder(binding: MobilityModuleItemBinding) :
        ViewHolder<MobilityModule>(binding) {
        constructor(parent: ViewGroup) : this(
            MobilityModuleItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        override fun bind(item: MobilityModule) {

        }
    }

    override fun createItemViewHolder(parent: ViewGroup): ViewHolder<MobilityModule> =
        MobilityModuleViewHolder(parent)
}