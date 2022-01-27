package com.als.l2.repository

import com.als.l2.model.WeatherDTO
import retrofit2.Callback


interface IDetailsRepository {
    fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: Callback<WeatherDTO>
    )

}