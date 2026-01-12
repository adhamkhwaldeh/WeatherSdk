package com.adham.weatherSdk.data.dtos

import com.squareup.moshi.JsonClass

/**
 * Current weather response
 *
 * @property count
 * @property data
 * @constructor Create empty Current weather response
 */
@JsonClass(generateAdapter = true)
data class CurrentWeatherResponse(
    val count: Int,
    val data: List<CurrentWeatherModel>,
)
