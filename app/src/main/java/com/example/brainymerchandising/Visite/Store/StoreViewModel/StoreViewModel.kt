package com.example.brainymerchandising.Visite.Store.StoreViewModel

import androidx.lifecycle.ViewModel
import com.example.brainymerchandising.Visite.Store.Repository.StoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StoreViewModel @Inject constructor(
    private val storeRepository: StoreRepository
) : ViewModel() {
    suspend fun getStores() = storeRepository.getStores()
}