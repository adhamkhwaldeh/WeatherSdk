package com.adham.gini.weatherginisdk.data.dtos

/**
 * Current weather response
 *
 * @property count
 * @property data
 * @constructor Create empty Current weather response
 */
data class CurrentWeatherResponse(
    val count: Int,
    val data: MutableList<CurrentWeatherModel>
)
