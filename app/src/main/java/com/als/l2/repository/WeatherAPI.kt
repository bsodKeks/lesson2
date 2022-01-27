package com.als.l2.repository

import com.als.l2.model.WeatherDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface WeatherAPI {
    @GET("v2/informers")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double
    ): Call<WeatherDTO>

}