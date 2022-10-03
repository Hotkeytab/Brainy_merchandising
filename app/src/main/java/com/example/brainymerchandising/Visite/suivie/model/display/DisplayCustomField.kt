package com.example.brainymerchandising.Visite.suivie.model.display

import com.google.gson.annotations.SerializedName


data class DisplayCustomField (

    @SerializedName("id"               ) var id               : Int?    = null,
    @SerializedName("name"             ) var name             : String? = null,
    @SerializedName("type"             ) var type             : String? = null,
    @SerializedName("createdAt"        ) var createdAt        : String? = null,
    @SerializedName("updatedAt"        ) var updatedAt        : String? = null,
    @SerializedName("displaySectionId" ) var displaySectionId : Int?    = null

)