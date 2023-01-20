package com.flow.flowandroid.utils

import com.flow.flowandroid.network.ApiService
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppConfig {
    // Base url of the api
    private val BASE_URL = "https://api.openweathermap.org/"

    // create retrofit service
    fun apiService(): ApiService =
        Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
            .create(ApiService::class.java)
}