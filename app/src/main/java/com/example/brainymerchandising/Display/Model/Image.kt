package com.example.brainymerchandising.Display.Model

import android.graphics.Bitmap

data class Image(
    var id: Int,
    var url: Bitmap,
    var text: String,
    var SectionId : Int,
    var path : String,

    ) {
}