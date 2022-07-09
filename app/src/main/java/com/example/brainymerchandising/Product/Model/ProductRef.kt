package com.example.brainymerchandising.Product.Model

import com.example.brainymerchandising.Visite.Store.Model.Store

data class ProductRef(
   val  storeId : Int,
   val productId: Int,
   val available : Boolean,
   val createdAt : String,
   val updatedAt :String,
   val product : Product,
   val store : Store
)
