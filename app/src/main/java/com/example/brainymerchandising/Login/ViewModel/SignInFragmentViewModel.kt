package com.example.brainymerchandising.Login.ViewModel

import androidx.lifecycle.ViewModel
import com.example.brainymerchandising.Login.Model.UserInfo_LoginPost
import com.example.brainymerchandising.Login.Repository.AuthRepository

import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInFragmentViewModel @Inject constructor(
    private val authRepository: AuthRepository,

    ) : ViewModel(){
    suspend fun login(signinObject: UserInfo_LoginPost) = authRepository.login(signinObject)
    suspend fun getUser(username: String) = authRepository.getUser(username)}