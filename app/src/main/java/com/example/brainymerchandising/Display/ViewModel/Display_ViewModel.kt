package com.example.brainymerchandising.Display.ViewModel

import androidx.lifecycle.ViewModel
import com.example.brainymerchandising.Display.Repository.Display_Repository
import com.example.brainymerchandising.Visite.Store.Repository.StoreService
import com.example.brainymerchandising.Visite.visite.Model.VisitPost
import com.example.brainymerchandising.Visite.visite.Repository.VisiteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class Display_ViewModel @Inject constructor(
    private val displayRepository: Display_Repository
) : ViewModel() {

    suspend fun getDisplayType()= displayRepository.getDisplayType()
    suspend fun getDisplayCategory()= displayRepository.getDisplayCategory()
    suspend fun getDisplayBrand()= displayRepository.getDisplayBrand()
    suspend fun postDisplay(files: ArrayList<MultipartBody.Part?>, display: RequestBody,customValues: RequestBody,data: RequestBody) =
        displayRepository.postDisplay(files,display,customValues,data)
}
