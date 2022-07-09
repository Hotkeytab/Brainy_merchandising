package com.example.brainymerchandising.Display.Repository

import com.example.brainymerchandising.Utils.resources.BaseRemoteDataSource
import com.example.brainymerchandising.Visite.Store.Repository.StoreService
import javax.inject.Inject

class Display_Repository @Inject constructor(
    private val display_type : Display_Service
) : BaseRemoteDataSource() {

    suspend fun getDisplayType() = getResult { display_type.getDisplayType() }
    suspend fun getDisplayCategory() = getResult { display_type.getDisplayCategory() }
    suspend fun getDisplayBrand() = getResult { display_type.getDisplayBrand() }



}