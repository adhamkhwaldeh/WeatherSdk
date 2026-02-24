package com.adham.weatherSdk.data.remote.dtos.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Hourly forecast model
 *
 * @property temp
 * @property timeStampLocal
 * @property weather
 * @constructor Create empty Hourly forecast model
 */
@JsonClass(generateAdapter = true)
data class HourlyForecastModel(
    val temp: Double,
    @param:Json(name = "timestamp_local")
    val timeStampLocal: String,
    val weather: WeatherModel,
)
