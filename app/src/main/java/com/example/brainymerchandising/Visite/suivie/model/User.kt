package com.example.brainymerchandising.Visite.suivie.model

import com.google.gson.annotations.SerializedName


data class User (

    @SerializedName("id") val id : Int,
    @SerializedName("first_name") val first_name : String,
    @SerializedName("last_name") val last_name : String,
    @SerializedName("gender") val gender : String,
    @SerializedName("username") val username : String,
    @SerializedName("password") val password : String,
    @SerializedName("email") val email : String,
    @SerializedName("phone_number") val phone_number : Int,
    @SerializedName("profile_picture") val profile_picture : String,
    @SerializedName("enabled") val enabled : Boolean,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("roleId") val roleId : Int
)