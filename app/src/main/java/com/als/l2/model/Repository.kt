package com.als.l2.model

class Repository: IRepository {
    override fun getWeatherFromServer(): City = City(name = "Moskow", temp = -20)

    fun doSomthing(){

    }
}