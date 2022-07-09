package com.example.brainymerchandising.Product.Model

import com.example.brainymerchandising.Visite.Store.Model.Store

data class Product(
    val id : Int,
    val label : String,
    val barcode : String,
    val typology : Int,
    val path : String,
    val enabled : Boolean,
    val createdAt : String,
    val updatedAt : String,
    val categoryId : Int,
    val brandId : Int,
    val brand : Brand,
    val category : Category,
    val internalCodes :  List<InternalCode>,
    val quantity : Int

)
