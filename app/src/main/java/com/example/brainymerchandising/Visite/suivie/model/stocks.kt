package com.example.brainymerchandising.Visite.suivie.model

import com.google.gson.annotations.SerializedName

data class Stocks (

    @SerializedName("id") val id : Int,
    @SerializedName("quantity") val quantity : Int,
    @SerializedName("stockOut") val stockOut : Boolean,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("visitId") val visitId : Int,
    @SerializedName("productId") val productId : Int,
    @SerializedName("storeId") val storeId : Int,
    @SerializedName("userId") val userId : Int,
    @SerializedName("product") val product : Product
)
