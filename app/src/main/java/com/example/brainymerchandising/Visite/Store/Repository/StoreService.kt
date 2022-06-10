package com.example.brainymerchandising.Visite.Store.Repository

import com.example.brainymerchandising.Visite.Store.Model.GetStore
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface StoreService {
    @GET("store/")
    suspend fun getStores() : Response<GetStore>

  /*
    @Multipart
    @POST("store/")
    suspend fun modifyStore(@Part("store") store: RequestBody) : Response<ModifyStoreResponse>
  */

}
