package com.adham.weatherSdk.networking

import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse

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
