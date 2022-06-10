package com.example.brainymerchandising.Utils.resources.Server_date.Model

import com.google.gson.annotations.SerializedName

data class Get_date_Model(
    val succes : Int,
    @SerializedName("data")
    val Date : String
)
