package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeatherMapModel(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)
