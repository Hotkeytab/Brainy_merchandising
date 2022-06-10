package com.example.brainymerchandising.Login.Repository


import com.example.brainymerchandising.Login.Model.UserInfo_LoginPost
import com.example.brainymerchandising.Utils.resources.BaseRemoteDataSource
import javax.inject.Inject

class AuthRepository  @Inject constructor(
    private val authService: AuthService
): BaseRemoteDataSource(){
    suspend fun login(signinObject: UserInfo_LoginPost) = getResult { authService.login(signinObject) }
    suspend fun getUser(username: String) = getResult { authService.getUser(username) }
}