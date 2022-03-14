package com.example.disciplines.ui.listUtils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.disciplines.R
import com.example.disciplines.data.network.model.DisciplinesPair
import com.example.disciplines.data.network.model.MobilityModule
import com.example.disciplines.databinding.*


class HeaderViewHolder private constructor(
    binding: ListHeaderBinding,
    header: Header
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.header = header
    }

    constructor(parent: ViewGroup, header: Header) : this(
        ListHeaderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        header
    )
}

class ButtonViewHolder private constructor(
    binding: ListButtonBinding,
    listener: View.OnClickListener
) :
    RecyclerView.ViewHolder(binding.root) {
    init {
        binding.button.setOnClickListener(listener)
    }

    constructor(parent: ViewGroup, listener: View.OnClickListener) : this(
        ListButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        ),
        listener
    )
}

abstract class ViewHolder<T>(binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    abstract fun bind(item: T)
}
