package com.als.l2.utils

import com.als.l2.model.*
import com.als.l2.model.db.HistoryEntity

fun convertDtoToModel(weatherDTO: WeatherDTO): List<Weather> {
    val fact: FactDTO = weatherDTO.factical!!
    return listOf(Weather(getDefaultCity(), fact.temp!!, fact.feels_like!!, fact.condition!!, fact.icon!!))
}

fun convertHistoryEntityToWeather(entityList: List<HistoryEntity>): List<Weather> {
    return entityList.map {
        Weather(City(it.city, 0.0, 0.0), it.temperature, 0, it.conditional)
    }
}

fun convertWeatherToEntity(weather: Weather): HistoryEntity =
    HistoryEntity(0, weather.city.city, weather.temperature, weather.condition)

