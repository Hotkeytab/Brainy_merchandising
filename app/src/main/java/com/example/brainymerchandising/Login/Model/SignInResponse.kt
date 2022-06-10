package com.example.brainymerchandising.Login.Model

import com.google.gson.annotations.SerializedName

data class SignInResponse(
    @SerializedName("succes")
    val success: Int,
    val message: String,
    @SerializedName("data")
    val token: String

){}
