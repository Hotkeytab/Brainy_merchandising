package com.example.brainymerchandising.Display.Model.Post

import com.example.brainymerchandising.Display.Model.Image

data class ItemsList_form (
    val listCustomfields : List<CustomFieldValue>,
    val listImage : List<Image>
){
}