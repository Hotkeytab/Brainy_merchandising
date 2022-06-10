package com.example.brainymerchandising.Utils.resources.Server_date.Repository

import com.example.brainymerchandising.Utils.resources.Server_date.Model.Get_date_Model
import retrofit2.Response
import retrofit2.http.GET

interface Date_service {

    @GET("utils/getSysDate")
    suspend fun getDatetime(): Response<Get_date_Model>
}