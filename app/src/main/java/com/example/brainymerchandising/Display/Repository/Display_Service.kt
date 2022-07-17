package com.example.brainymerchandising.Display.Repository

import com.example.brainymerchandising.Display.Model.DisplayType
import com.example.brainymerchandising.Display.Model.Display_Category_Get_Response
import com.example.brainymerchandising.Display.Model.ListGetResponse_DisplayType
import com.example.brainymerchandising.Display.Model.List_Brand_Get_Response
import com.example.brainymerchandising.Utils.resources.ConstModele.SuccessResponse
import com.example.brainymerchandising.Visite.Store.Model.GetStore
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface Display_Service {

    @GET("/displayType")
    suspend fun getDisplayType() : Response<ListGetResponse_DisplayType>

    @GET("/category")
    suspend fun getDisplayCategory() : Response<Display_Category_Get_Response>

    @GET("/brand")
    suspend fun getDisplayBrand() : Response<List_Brand_Get_Response>

    @Multipart
    @POST("/display")
    suspend fun postDisplay(@Part files: ArrayList<MultipartBody.Part?>, @Part("display") display: RequestBody,
                            @Part("customValues") customValues: RequestBody, @Part("data") data: RequestBody)
                              : Response<SuccessResponse>

    @Multipart
    @POST("/display")
    suspend fun postDisplay1(@Part files: ArrayList<MultipartBody.Part?>, @Part("display") display: RequestBody,
                            @Part("customValues") customValues: RequestBody, @Part("data") data: RequestBody)
            : Response<SuccessResponse>

}