package com.example.brainymerchandising.Display.Repository

import com.example.brainymerchandising.Utils.resources.BaseRemoteDataSource
import com.example.brainymerchandising.Visite.Store.Repository.StoreService
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class Display_Repository @Inject constructor(
    private val display_type : Display_Service
) : BaseRemoteDataSource() {

    suspend fun getDisplayType() = getResult { display_type.getDisplayType() }
    suspend fun getDisplayCategory() = getResult { display_type.getDisplayCategory() }
    suspend fun getDisplayBrand() = getResult { display_type.getDisplayBrand() }
    suspend fun postDisplay(files: ArrayList<MultipartBody.Part?>, display: RequestBody,customValues: RequestBody,data: RequestBody,  )
    = getResult{ display_type.postDisplay(files,display,customValues,data) }



}