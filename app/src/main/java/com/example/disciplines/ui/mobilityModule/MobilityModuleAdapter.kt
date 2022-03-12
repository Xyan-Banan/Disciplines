package com.example.disciplines.ui.mobilityModule

import android.view.View
import android.view.ViewGroup
import com.example.disciplines.data.network.model.MobilityModule
import com.example.disciplines.ui.listUtils.AbstractListAdapter
import com.example.disciplines.ui.listUtils.MobilityModuleViewHolder
import com.example.disciplines.ui.listUtils.ViewHolder

class MobilityModuleAdapter(
    items: List<MobilityModule>,
    headerText: String,
    buttonListener: View.OnClickListener
) :
    AbstractListAdapter<MobilityModule>(items, headerText, buttonListener) {
    override fun createItemViewHolder(parent: ViewGroup): ViewHolder<MobilityModule> =
        MobilityModuleViewHolder(parent)
}