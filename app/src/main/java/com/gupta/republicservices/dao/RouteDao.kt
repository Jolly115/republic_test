package com.gupta.republicservices.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gupta.republicservices.models.Route

@Dao
interface RouteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(routes: List<Route>)

    @Query("SELECT * FROM routes WHERE id = :driverId LIMIT 1")
    suspend fun getRouteById(driverId: Int): Route?

    @Query("SELECT * FROM routes WHERE type = 'R' AND id = (SELECT MIN(id) FROM routes WHERE type = 'R')")
    suspend fun getFirstRTypeRoute(): Route?

    @Query("SELECT * FROM routes WHERE type = 'C' AND id = (SELECT id FROM routes WHERE type = 'C' ORDER BY id ASC LIMIT 1 OFFSET 1)")
    suspend fun getSecondCTypeRoute(): Route?

    @Query("SELECT * FROM routes WHERE type = 'I' AND id = (SELECT MAX(id) FROM routes WHERE type = 'I')")
    suspend fun getLastITypeRoute(): Route?

    suspend fun getRouteByDriverId(driverId: Int): Route? {
        val route = getRouteById(driverId)
        return when {
            route != null -> route
            driverId % 2 == 0 -> getFirstRTypeRoute()
            driverId % 5 == 0 -> getSecondCTypeRoute()
            else -> getLastITypeRoute()
        }
    }

}