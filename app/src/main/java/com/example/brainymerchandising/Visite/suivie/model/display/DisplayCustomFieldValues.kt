package com.example.brainymerchandising.Visite.suivie.model.display

import com.google.gson.annotations.SerializedName

data class DisplayCustomFieldValues (

    @SerializedName("id"                   ) var id                   : Int?                = null,
    @SerializedName("value"                ) var value                : String?             = null,
    @SerializedName("createdAt"            ) var createdAt            : String?             = null,
    @SerializedName("updatedAt"            ) var updatedAt            : String?             = null,
    @SerializedName("displayCustomFieldId" ) var displayCustomFieldId : Int?                = null,
    @SerializedName("displayId"            ) var displayId            : Int?                = null,
    @SerializedName("displayCustomField"   ) var displayCustomField   : DisplayCustomField? = DisplayCustomField()

)