package com.example.brainymerchandising.Display.Model.Post

import com.example.brainymerchandising.Display.Model.DisplayCustomFields
import com.example.brainymerchandising.Display.Model.Image

data class Items_Text_input (
    val field : DisplayCustomFields,
    val value : String,
    val displayCustomFieldId : Int
){
}