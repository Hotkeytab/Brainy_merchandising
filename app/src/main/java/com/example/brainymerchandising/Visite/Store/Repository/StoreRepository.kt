package com.example.brainymerchandising.Visite.Store.Repository

import com.example.brainymerchandising.Utils.resources.BaseRemoteDataSource
import com.example.brainymerchandising.Visite.visite.Repository.VisiteService
import okhttp3.RequestBody
import retrofit2.http.Part
import javax.inject.Inject

class StoreRepository @Inject constructor(
    private val storeservice: StoreService
) : BaseRemoteDataSource() {

    suspend fun getStores() = getResult { storeservice.getStores() }
/*
    suspend fun modifyStore(@Part store: RequestBody) = getResult {storeservice.modifyStore(store)}
*/


}