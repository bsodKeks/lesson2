package com.als.l2.repository

import com.als.l2.model.Weather
import com.als.l2.model.db.HistoryDao
import com.als.l2.utils.convertHistoryEntityToWeather
import com.als.l2.utils.convertWeatherToEntity

class LocalRepository(private val localDataSource: HistoryDao): ILocalRepository {
    override fun getAllHistory(): List<Weather> =
        convertHistoryEntityToWeather(localDataSource.all())

    override fun saveEntity(weather: Weather) {
        localDataSource.insert(convertWeatherToEntity(weather))
    }
}