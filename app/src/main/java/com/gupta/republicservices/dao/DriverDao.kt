package com.gupta.republicservices.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gupta.republicservices.models.Driver

@Dao
interface DriverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(drivers: List<Driver>)

    @Query("SELECT * FROM drivers ORDER BY lastName ASC")
    fun getAllSortedByLastName(): LiveData<List<Driver>>

    @Query("SELECT * FROM drivers")
    fun getAll(): LiveData<List<Driver>>
}


