package com.example.brainymerchandising.Visite.suivie.model.display

import com.google.gson.annotations.SerializedName


data class DisplayType (

    @SerializedName("id"              ) var id              : Int?                       = null,
    @SerializedName("name"            ) var name            : String?                    = null,
    @SerializedName("abbreviation"    ) var abbreviation    : String?                    = null,
    @SerializedName("withBrand"       ) var withBrand       : Boolean?                   = null,
    @SerializedName("withCategory"    ) var withCategory    : Boolean?                   = null,
    @SerializedName("createdAt"       ) var createdAt       : String?                    = null,
    @SerializedName("updatedAt"       ) var updatedAt       : String?                    = null,
    @SerializedName("displaySections" ) var displaySections : ArrayList<DisplaySections> = arrayListOf()

)