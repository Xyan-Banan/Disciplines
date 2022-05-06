package com.example.disciplines.data.network.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

sealed class Selected: Parcelable {
    @Parcelize
    data class Discipline(val value: com.example.disciplines.data.network.model.Discipline): Selected()

    @Parcelize
    data class List(val value: kotlin.collections.List<com.example.disciplines.data.network.model.Discipline>): Selected()

    @Parcelize
    data class BundleList(val value: kotlin.collections.List<Bundle>):Selected()
}

@Parcelize
data class Bundle(val list: List<Discipline>, var checkedIndex: Int?): Parcelable