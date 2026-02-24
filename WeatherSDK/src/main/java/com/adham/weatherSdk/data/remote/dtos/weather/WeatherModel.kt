package com.adham.weatherSdk.data.remote.dtos.weather

import com.squareup.moshi.JsonClass

/**
 * Weather model
 *
 * @property description
 * @constructor Create empty Weather model
 */
@JsonClass(generateAdapter = true)
data class WeatherModel(
    val description: String,
)
