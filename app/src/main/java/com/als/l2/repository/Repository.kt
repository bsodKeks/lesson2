package com.als.l2.repository

import com.als.l2.model.Weather
import com.als.l2.model.getRussianCities
import com.als.l2.model.getWorldCities

class Repository() : IRepository {

    override fun getWeatherFromServer(): Weather {
        return Weather()
    }

    override fun getWeatherFromLocalStorageRus(): List<Weather> {
        return getRussianCities()
    }

    override fun getWeatherFromLocalStorageWorld(): List<Weather> {
        return getWorldCities()
    }

}