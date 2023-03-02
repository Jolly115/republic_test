package com.gupta.republicservices.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "dataResponse")
@JsonClass(generateAdapter = true)
data class ApiResponse(
    val drivers: List<Driver>,
    val routes: List<Route>
) : Parcelable

@Parcelize
@Entity(tableName = "drivers")
@JsonClass(generateAdapter = true)
data class Driver(
    @PrimaryKey val id: String,
    val name: String,
    val lastName: String?,
) : Parcelable

@Parcelize
@Entity(tableName = "routes")
@JsonClass(generateAdapter = true)
data class Route(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String
) : Parcelable
