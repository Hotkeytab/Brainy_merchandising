package com.example.brainymerchandising.Product.Repository

import com.example.brainymerchandising.Product.Model.GetRefProduct_Response
import com.example.brainymerchandising.Product.Model.POST.productPost
import com.example.brainymerchandising.Utils.resources.ConstModele.SuccessResponse
import com.example.brainymerchandising.Visite.visite.Model.VisitPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface Product_Service {

    @GET("/referencedProduct/{storeId}")
    suspend fun getRefProduct(@Path("storeId") storeId: Int?) : Response<GetRefProduct_Response>

    @POST("/stock")
    suspend fun updateStock(@Body product_post: List<productPost>) : Response<SuccessResponse>

}