package com.example.brainymerchandising.Utils.resources.Server_date.ViewModel

import androidx.lifecycle.ViewModel
import com.example.brainymerchandising.Login.Model.UserInfo_LoginPost
import com.example.brainymerchandising.Login.Repository.AuthRepository
import com.example.brainymerchandising.Utils.resources.Server_date.Repository.Date_Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Date_ViewModel @Inject constructor(
    private val dateRepository: Date_Repository,

    ) : ViewModel() {
    suspend fun getDatetime() = dateRepository.getDatetime()


}