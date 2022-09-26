package com.example.brainymerchandising.Visite.visite.ViewModel

import androidx.lifecycle.ViewModel
import com.example.brainymerchandising.Visite.visite.Model.VisitPost
import com.example.brainymerchandising.Visite.visite.Repository.VisiteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VisiteViewModel @Inject constructor(
    private val visiteRepository: VisiteRepository
) : ViewModel() {

    suspend fun getVisites(user_id: String, date_begin: String, date_end: String) =
        visiteRepository.getVisites(user_id, date_begin, date_end)

    suspend fun getVisitesSuivie(user_id: String, date_begin: String, date_end: String) =
        visiteRepository.getVisitesSuivie(user_id, date_begin, date_end)




    suspend fun addVisite(visitPost: ArrayList<VisitPost>) = visiteRepository.addVisit(visitPost)


    /*
    suspend fun getSurveyResponse(user_id: String, date_begin: String, date_end: String) =
        visiteRepository.getSurveyResponse(user_id, date_begin, date_end)
*/
    suspend fun deleteVisite(visiteId: Int) = visiteRepository.deleteVisite(visiteId)
}