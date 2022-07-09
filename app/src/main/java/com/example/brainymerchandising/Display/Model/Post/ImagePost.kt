package com.example.brainymerchandising.Display.Model.Post

data class ImagePost (
    val id : Int,
    val path : String,
    val description : String,
    val createdAt : String,
    val updatedAt : String,
    val displaySectionId : Int,
    val displayTypeId : Int

        ){


}