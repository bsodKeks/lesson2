package com.als.l2.repository

import com.als.l2.model.Weather


interface ILocalRepository {
    fun getAllHistory(): List<Weather>
    fun saveEntity(weather: Weather)
}