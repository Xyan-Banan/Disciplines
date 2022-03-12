package com.example.disciplines.data.network.model

data class MobilityModule(
    val name: String,
    val platform: String,
    val intensity: Int,
    val hours: Int,
    val link: String = ""
)
