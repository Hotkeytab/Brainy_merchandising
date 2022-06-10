package com.example.brainymerchandising.Visite.Store.Model

import com.google.gson.annotations.SerializedName

data class GetStore(
    @SerializedName("data")
    val liste_store: List<Store>,
    val succes: Int
)
