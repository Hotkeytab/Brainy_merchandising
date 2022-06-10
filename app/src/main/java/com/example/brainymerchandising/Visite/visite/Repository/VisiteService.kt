package com.example.brainymerchandising.Visite.visite.Repository

import com.example.brainymerchandising.Utils.resources.ConstModele.SuccessResponse
import com.example.brainymerchandising.Visite.visite.Model.DeleteVisiteResponse
import com.example.brainymerchandising.Visite.visite.Model.ListVisiteGet
import com.example.brainymerchandising.Visite.visite.Model.VisitPost
import retrofit2.Response
import retrofit2.http.*

interface VisiteService {

    @GET("/visit/{user_id}/{date_begin}/{date_end}")
    suspend fun getVisites(@Path("user_id") user_id: String, @Path("date_begin") date_begin: String, @Path("date_end") date_end: String): Response<ListVisiteGet>

    @DELETE("/visit/{visitId}")
    suspend fun deleteVisite(@Path("visitId") visitId : Int) : Response<DeleteVisiteResponse>

    @POST("visit")
    suspend fun addVisit(@Body visitPost: ArrayList<VisitPost>) : Response<SuccessResponse>
/*
    @GET("/surveyResponse/{user_id}/{date_begin}/{date_end}")
    suspend fun getSurveyResponse(@Path("user_id") user_id: String, @Path("date_begin") date_begin: String, @Path("date_end") date_end: String): Response<SurveyResponse>

*/
}
