package com.adham.weatherSdk.data.remote.dtos.weather

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Current weather model
 *
 * @property temp
 * @property ts
 * @property weather
 * @constructor Create empty Current weather model
 */
@JsonClass(generateAdapter = true)
data class CurrentWeatherModel(
    @param:Json(name = "city_name")
    val cityName: String,
    val temp: Double,
    val ts: Long,
    val weather: WeatherModel,
)
