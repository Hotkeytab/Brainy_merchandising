package com.example.brainymerchandising.Visite.suivie.model

import com.google.gson.annotations.SerializedName

data class Product (

    @SerializedName("id") val id : Int,
    @SerializedName("label") val label : String,
    @SerializedName("barcode") val barcode : Int,
    @SerializedName("typology") val typology : Int,
    @SerializedName("path") val path : String,
    @SerializedName("enabled") val enabled : Boolean,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("categoryId") val categoryId : Int,
    @SerializedName("brandId") val brandId : Int
)