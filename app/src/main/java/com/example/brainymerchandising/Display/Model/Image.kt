package com.example.brainymerchandising.Display.Model

import android.graphics.Bitmap

data class Image(
    val id: Int,
    var url: Bitmap,
    var text: String,

    ) {
}