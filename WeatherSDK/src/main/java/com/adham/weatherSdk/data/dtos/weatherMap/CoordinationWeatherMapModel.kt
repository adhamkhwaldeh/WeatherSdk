package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CoordinationWeatherMapModel(
    val lon: Double,
    val lat: Double,
)
