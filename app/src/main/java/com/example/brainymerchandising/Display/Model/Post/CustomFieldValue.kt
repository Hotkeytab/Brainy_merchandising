package com.example.brainymerchandising.Display.Model.Post

data class CustomFieldValue(
    val id : Int,
    var value : String,
    var createdAt : String,
    var updatedAt : String,
    var DisplayCustomFieldId : Int,
    var displayId : Int)
