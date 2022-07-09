package com.example.brainymerchandising.Product.Repository

import com.example.brainymerchandising.Utils.resources.BaseRemoteDataSource
import javax.inject.Inject

class Store_Repository @Inject constructor(
    private val productservice : Product_Service
) : BaseRemoteDataSource() {

    suspend fun getRefProduct(storeId : Int) = getResult { productservice.getRefProduct(storeId) }




}