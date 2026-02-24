package com.adham.weatherSdk.domain.repositories

import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.data.remote.dtos.weather.ForecastResponse

/**
 * Weather repository imp
 *
 * @constructor Create empty Weather repository imp
 */
interface WeatherRepository {
    /**
     * Current
     *
     * @param city
     * @param apiKey
     * @return
     */
    suspend fun current(
        city: String,
        apiKey: String,
    ): Result<CurrentWeatherResponse>

    /**
     * Forecast
     *
     * @param city
     * @param hours
     * @param apiKey
     * @return
     */
    suspend fun forecast(
        city: String,
        hours: Int,
        apiKey: String,
    ): Result<ForecastResponse>
}
