package com.gupta.republicservices.network


class RemoteDataSource(
    private val apiService: ApiService
) : BaseDataSource() {

    suspend fun getData() = getResult {
        apiService.getData()
    }

}