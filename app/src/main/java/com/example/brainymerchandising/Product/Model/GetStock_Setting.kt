package com.example.brainymerchandising.Product.Model

import com.example.brainymerchandising.Product.Model.ProductRef

data class GetStock_Setting(
    val succes : Int,
    val data : List<StockSetting>)
