package com.example.brainymerchandising.Display.Repository

import com.example.brainymerchandising.Display.Model.DisplayType
import com.example.brainymerchandising.Display.Model.Display_Category_Get_Response
import com.example.brainymerchandising.Display.Model.ListGetResponse_DisplayType
import com.example.brainymerchandising.Display.Model.List_Brand_Get_Response
import com.example.brainymerchandising.Visite.Store.Model.GetStore
import retrofit2.Response
import retrofit2.http.GET

interface Display_Service {

    @GET("/displayType")
    suspend fun getDisplayType() : Response<ListGetResponse_DisplayType>

    @GET("/category")
    suspend fun getDisplayCategory() : Response<Display_Category_Get_Response>

    @GET("/brand")
    suspend fun getDisplayBrand() : Response<List_Brand_Get_Response>
}