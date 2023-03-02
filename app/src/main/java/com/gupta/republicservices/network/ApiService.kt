package com.gupta.republicservices.network

import com.gupta.republicservices.models.ApiResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("data")
    suspend fun getData(): Response<ApiResponse>

}
