package com.example.brainymerchandising.Product.Model.POST

import com.example.brainymerchandising.Product.Model.Product
import com.example.brainymerchandising.Visite.Store.Model.Store

data class productPost(

    var product: Product,
    var store: Store,
    var productId: Int,
    var storeId: Int,
    var stockOut: Boolean,

    var quantity: Int?,
    var visitId: Int
)
