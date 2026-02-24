package com.adham.weatherSdk.data.remote.networking

import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.data.remote.dtos.weather.ForecastResponse

interface BaseWeatherServiceApi {
    suspend fun current(
        city: String,
        key: String,
    ): CurrentWeatherResponse

    suspend fun forecast(
        city: String,
        hours: Int,
        key: String,
    ): ForecastResponse
}
