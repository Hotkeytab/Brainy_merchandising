package com.example.brainymerchandising.Visite.suivie.model

import com.google.gson.annotations.SerializedName

data class VisiteSuivieGet(

    @SerializedName("succes") val succes : Int,
    @SerializedName("data") val data : List<VisiteSuivie>

)
