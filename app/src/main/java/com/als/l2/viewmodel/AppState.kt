package com.als.l2.viewmodel

import com.als.l2.model.City
import com.als.l2.model.Weather

sealed class AppState {
    data class Success(val weatherData: List<Weather>) : AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}
