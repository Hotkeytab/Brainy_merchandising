package com.example.brainymerchandising.Visite.suivie.model

import com.google.gson.annotations.SerializedName
data class Store (

    @SerializedName("id") val id : Int,
    @SerializedName("name") val name : String,
    @SerializedName("address") val address : String,
    @SerializedName("governorate") val governorate : String,
    @SerializedName("postal_code") val postal_code : Int,
    @SerializedName("type") val type : String,
    @SerializedName("email") val email : String,
    @SerializedName("phone_number") val phone_number : Int,
    @SerializedName("lat") val lat : Double,
    @SerializedName("lng") val lng : Double,
    @SerializedName("path") val path : String,
    @SerializedName("enabled") val enabled : Boolean,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("storeGroupId") val storeGroupId : Int
)