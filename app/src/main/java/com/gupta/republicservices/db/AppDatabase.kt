package com.gupta.republicservices.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gupta.republicservices.dao.DriverDao
import com.gupta.republicservices.dao.RouteDao
import com.gupta.republicservices.models.Driver
import com.gupta.republicservices.models.Route

@Database(entities = [Driver::class, Route::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun driverDao(): DriverDao
    abstract fun routeDao(): RouteDao
}

fun provideDriverDataDao(db: AppDatabase) = db.driverDao()
fun provideRouteDataDao(db: AppDatabase) = db.routeDao()



