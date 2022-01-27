package com.als.l2.repository

import com.als.l2.model.WeatherDTO
import retrofit2.Callback

class DetailsRepository(private val remoteDataSource: RemoteDataSource): IDetailsRepository {
    override fun getWeatherDetailsFromServer(
        lat: Double,
        lon: Double,
        callback: Callback<WeatherDTO>
    ) {
        remoteDataSource.getWeatherDetails(lat, lon, callback)
    }

}