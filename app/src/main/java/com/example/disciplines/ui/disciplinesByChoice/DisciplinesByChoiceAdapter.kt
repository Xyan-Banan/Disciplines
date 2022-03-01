package com.example.disciplines.ui.disciplinesByChoice

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.disciplines.data.database.DisciplinesPair
import com.example.disciplines.databinding.DisciplinesByChoiceListHeaderFragmentBinding
import com.example.disciplines.databinding.DisciplinesByChoiceListItemFragmentBinding
import com.example.disciplines.databinding.ListButtonBinding

enum class ListItemType {
    Header,
    Discipline,
    Button
}

class DisciplinesByChoiceAdapter(
    disciplines: List<DisciplinesPair>,
    private val headerText: String
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val values: List<ListItem> = addHeaderAndButton(disciplines)

    private fun addHeaderAndButton(disciplines: List<DisciplinesPair>): List<ListItem> = buildList {
        add(ListItem.Header)
        if (disciplines.isNotEmpty()) {
            addAll(disciplines.map { ListItem.DisciplineWrapper(it) })
            add(ListItem.Button)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ListItemType.Header.ordinal -> HeaderViewHolder.from(parent, headerText)
            ListItemType.Discipline.ordinal -> ViewHolder.from(parent)
            else -> ButtonViewHolder.from(parent)
        }
    }


    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> ListItemType.Header.ordinal
            values.lastIndex -> ListItemType.Button.ordinal
            else -> ListItemType.Discipline.ordinal
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("OnBind", "$holder $position ${values.size}")
        when (holder) {
            is ViewHolder -> {
                val item = values[position] as ListItem.DisciplineWrapper
                holder.bind(item.pair)
            }
        }
    }

    override fun getItemCount(): Int = values.size

    class HeaderViewHolder private constructor(
        binding: DisciplinesByChoiceListHeaderFragmentBinding,
        text: String
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.instructions.text = text
        }

        companion object {
            fun from(parent: ViewGroup, text: String) = HeaderViewHolder(
                DisciplinesByChoiceListHeaderFragmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                text
            )
        }
    }

    class ViewHolder(private val binding: DisciplinesByChoiceListItemFragmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DisciplinesPair) {
            binding.disciplinePair = item
        }

        companion object {
            fun from(parent: ViewGroup) = ViewHolder(
                DisciplinesByChoiceListItemFragmentBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    class ButtonViewHolder(binding: ListButtonBinding) : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup) = ButtonViewHolder(
                ListButtonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

}

sealed class ListItem {
    object Header : ListItem() {
        override fun toString(): String {
            return "Header"
        }
    }

    data class DisciplineWrapper(val pair: DisciplinesPair) : ListItem() {
        override fun toString(): String {
            return "DisciplinePair"
        }
    }

    object Button : ListItem() {
        override fun toString(): String {
            return "Button"
        }
    }
}