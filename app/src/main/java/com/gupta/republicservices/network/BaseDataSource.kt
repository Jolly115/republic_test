package com.gupta.republicservices.network

import android.util.Log.e
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gupta.republicservices.models.ErrorResponse
import org.koin.core.logger.KOIN_TAG
import retrofit2.Response

abstract class BaseDataSource {
    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            val body = response.body()
            if (response.isSuccessful) {
                if (body != null) return Resource.success(body)
            } else {
                val gson = Gson()
                val type = object : TypeToken<ErrorResponse>() {}.type
                val errorResponse: ErrorResponse? =
                    gson.fromJson(response.errorBody()!!.charStream(), type)
                return Resource.error(errorResponse?.message, body, response.code())
            }
        } catch (e: Exception) {
            e(KOIN_TAG, e.message, e)
            return Resource.error(e.message ?: e.toString(), null, 429)
        }
        return Resource.error(null, null, 429)
    }
}