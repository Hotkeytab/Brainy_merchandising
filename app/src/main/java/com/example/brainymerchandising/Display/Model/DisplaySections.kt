package com.example.brainymerchandising.Display.Model

data class DisplaySections(
    val id : Int,
    val name : String,
    val createdAt : String,
    val updatedAt : String,
    val displayTypeId : Int,
    val  displayCustomFields : List<DisplayCustomFields>


    )
