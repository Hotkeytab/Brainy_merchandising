package com.example.brainymerchandising.Product.ViewModel

import androidx.lifecycle.ViewModel
import com.example.brainymerchandising.Product.Repository.Store_Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class Product_ViewModel @Inject constructor(
    private val storeRepository: Store_Repository
) : ViewModel() {
    suspend fun getRefProduct(storeId : Int) = storeRepository.getRefProduct(storeId)
}