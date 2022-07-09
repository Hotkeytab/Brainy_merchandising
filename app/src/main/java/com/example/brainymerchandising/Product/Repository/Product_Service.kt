package com.example.brainymerchandising.Product.Repository

import com.example.brainymerchandising.Product.Model.GetRefProduct_Response
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Product_Service {

    @GET("/referencedProduct/{storeId}")
    suspend fun getRefProduct(@Path("storeId") storeId: Int?) : Response<GetRefProduct_Response>

}