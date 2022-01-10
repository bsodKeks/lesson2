package com.als.l2.model

interface IRepository {
    fun getWeatherFromServer(): City
}