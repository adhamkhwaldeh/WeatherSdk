package com.adham.weatherSdk.data.dtos

import com.squareup.moshi.JsonClass

/**
 * Weather model
 *
 * @property description
 * @constructor Create empty Weather model
 */
@JsonClass(generateAdapter = true)
data class WeatherModel(
//    val icon: String,
    val description: String,
//    val code: Int,
)
