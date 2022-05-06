package com.example.disciplines.ui.listUtils

data class Header(val title: String, val instructions: CharSequence) {
    override fun equals(other: Any?): Boolean {
        if (other !is Header) return false
        return title == other.title && instructions.contentEquals(other.instructions)
    }

    override fun hashCode(): Int {
        var result = title.hashCode()
        result = 31 * result + instructions.hashCode()
        return result
    }
}
