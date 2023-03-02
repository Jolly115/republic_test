package com.gupta.republicservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gupta.republicservices.models.ApiResponse
import com.gupta.republicservices.network.Resource
import com.gupta.republicservices.repositories.ApiRepository

class MainViewModel(
    private val repository: ApiRepository
) : ViewModel() {

    fun getData(): LiveData<Resource<ApiResponse>> {
        return repository.getData()
    }

}


