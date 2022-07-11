package com.example.brainymerchandising.Product.Model.POST

import com.example.brainymerchandising.Product.Model.Product
import com.example.brainymerchandising.Visite.Store.Model.Store

data class productPost(

    val product: Product,
    val store: Store,
    val storeId: Int,
    val productId: Int,
    val quantity: Int,
    val stockOut: Boolean,
    val visitId: Int
)
