package com.adham.weatherSdk.data.dtos

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
