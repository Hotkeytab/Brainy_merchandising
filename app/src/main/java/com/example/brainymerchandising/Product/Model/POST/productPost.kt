package com.example.brainymerchandising.Product.Model.POST

data class productPost(
    val id: Int ,
    val storeId: Int,
val productId: Int,
val quantity: Int,
val stockOut: Boolean
)
