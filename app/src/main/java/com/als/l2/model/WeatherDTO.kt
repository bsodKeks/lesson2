package com.als.l2.model

import com.google.gson.annotations.SerializedName

data class WeatherDTO(
    @SerializedName("fact")
    val factical: FactDTO?
)