package com.flow.flowandroid.network

import com.flow.flowandroid.models.WeatherMainModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("data/2.5/forecast")
    suspend fun getWeatherDetails(
        @Query("lat")ll: String,
        @Query("lon")long: String,
        @Query("appid")appid: String): WeatherMainModel
}