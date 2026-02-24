package com.adham.weatherSdk.data.remote.dtos.weather

import com.squareup.moshi.JsonClass

/**
 * Forecast response
 *
 * @property data
 * @constructor Create empty Forecast response
 */
@JsonClass(generateAdapter = true)
data class ForecastResponse(
    val data: List<HourlyForecastModel>,
)
