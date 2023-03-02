package com.gupta.republicservices.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.gupta.republicservices.dao.RouteDao
import com.gupta.republicservices.models.Route

class RouteViewModel(private val driverId: String, private val routeDao: RouteDao) : ViewModel() {

    val route: LiveData<Route?> = liveData {
        emit(getRouteByDriverId(driverId.toInt()))
    }

    private suspend fun getRouteByDriverId(driverId: Int): Route? {
        return routeDao.getRouteByDriverId(driverId)
    }

}