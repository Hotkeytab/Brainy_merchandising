package com.example.brainymerchandising.Utils.resources.Server_date.Repository

import com.example.brainymerchandising.Login.Model.UserInfo_LoginPost
import com.example.brainymerchandising.Login.Repository.AuthService
import com.example.brainymerchandising.Utils.resources.BaseRemoteDataSource
import javax.inject.Inject

class Date_Repository @Inject constructor(
    private val dateservice: Date_service
): BaseRemoteDataSource(){
    suspend fun getDatetime() = getResult { dateservice.getDatetime() }

}
