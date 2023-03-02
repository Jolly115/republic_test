package com.gupta.republicservices.network

import com.google.gson.GsonBuilder
import com.gupta.republicservices.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

fun provideRetrofit(): Retrofit {
    val okHttpClient = OkHttpClient().newBuilder()
        .callTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Accept", "*/*")
                .build()
            chain.proceed(request)
        }
        .build()

    return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()


}

fun provideApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
