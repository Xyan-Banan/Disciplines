package com.example.disciplines.ui.listUtils

enum class ListItemType {
    Header,
    Item,
    Button
}

sealed class ListItem {
    object Header : ListItem() {
        override fun toString(): String {
            return "Header"
        }
    }

    data class Item<T>(val item: T) : ListItem() {
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