package com.example.brainymerchandising.Product.Repository

import com.example.brainymerchandising.Product.Model.POST.productPost
import com.example.brainymerchandising.Utils.resources.BaseRemoteDataSource
import javax.inject.Inject

class Store_Repository @Inject constructor(
    private val productservice : Product_Service
) : BaseRemoteDataSource() {

    suspend fun getRefProduct(storeId : Int) = getResult { productservice.getRefProduct(storeId) }
    suspend fun updateStock(product_post : List<productPost>) = getResult { productservice.updateStock(product_post) }




}