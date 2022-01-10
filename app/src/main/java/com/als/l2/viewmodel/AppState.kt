package com.als.l2.viewmodel

import com.als.l2.model.City

sealed class AppState {
    data class Success(val weatherData: City) : AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}
