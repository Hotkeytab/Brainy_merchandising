package com.example.brainymerchandising.Visite.suivie.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class VisiteSuivie(


    @SerializedName("id") val id : Int,
    @SerializedName("day") val day : String,
    @SerializedName("order") val order : Int,
    @SerializedName("planned") val planned : Boolean,
    @SerializedName("start") val start : String,
    @SerializedName("end") val end : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("storeId") val storeId : Int,
    @SerializedName("userId") val userId : Int,
    @SerializedName("store") val store : com.example.brainymerchandising.Visite.suivie.model.Store,
    @SerializedName("user") val user : User,
    @SerializedName("stocks") val stocks : List<Stocks>,
    @SerializedName("orders") val orders : List<String>,
    @SerializedName("displays") val displays : List<String>,
) : Serializable
