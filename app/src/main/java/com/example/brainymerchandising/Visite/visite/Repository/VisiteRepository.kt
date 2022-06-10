package com.example.brainymerchandising.Visite.visite.Repository

import com.example.brainymerchandising.Utils.resources.BaseRemoteDataSource
import com.example.brainymerchandising.Visite.visite.Model.VisitPost
import javax.inject.Inject

class VisiteRepository @Inject constructor(
    private val visiteService: VisiteService
) : BaseRemoteDataSource() {

    suspend fun getVisites(user_id: String, date_begin: String, date_end: String) =
        getResult { visiteService.getVisites(user_id, date_begin, date_end) }

    suspend fun deleteVisite(visitId : Int) = getResult { visiteService.deleteVisite(visitId) }

    suspend fun addVisit(visitPost: ArrayList<VisitPost>) = getResult { visiteService.addVisit(visitPost) }



    /*
    suspend fun getSurveyResponse(user_id: String, date_begin: String, date_end: String) =
        getResult { visiteService.getSurveyResponse(user_id, date_begin, date_end) }
*/
}