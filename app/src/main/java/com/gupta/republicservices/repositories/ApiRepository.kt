package com.gupta.republicservices.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.gupta.republicservices.dao.DriverDao
import com.gupta.republicservices.dao.RouteDao
import com.gupta.republicservices.extensions.extractLastName
import com.gupta.republicservices.models.ApiResponse
import com.gupta.republicservices.models.Driver
import com.gupta.republicservices.models.Route
import com.gupta.republicservices.network.RemoteDataSource
import com.gupta.republicservices.network.Resource
import kotlinx.coroutines.Dispatchers

class ApiRepository(
    private val remoteDataSource: RemoteDataSource,
    private val driverDao: DriverDao,
    private val routeDao: RouteDao
) {

    private suspend fun fetchDataAndSaveToDatabase(response: ApiResponse) {
        val drivers = response.drivers.map { driverResponse ->
            Driver(driverResponse.id, driverResponse.name, driverResponse.name.extractLastName())
        }
        val routes = response.routes.map { routeResponse ->
            Route(
                routeResponse.id,
                routeResponse.name,
                routeResponse.type
            )
        }
        driverDao.insertAll(drivers)
        routeDao.insertAll(routes)
    }

    fun getData(): LiveData<Resource<ApiResponse>> =
        liveData(Dispatchers.IO) {
            emit(Resource.loading(null))
            val response = remoteDataSource.getData()
            if (response.data != null) fetchDataAndSaveToDatabase(response.data)
        }

}
