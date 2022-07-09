package com.example.brainymerchandising.Display.Model

data class DisplayType(

    val id : Int,
    val name : String,
    val abbreviation : String,
    val withBrand : Boolean,
    val  withCategory : Boolean,
    val createdAt : String,
    val updatedAt : String,
    val displaySections : List<DisplaySections>

)
