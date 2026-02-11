package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WindWeatherMapModel(
    val speed: Double,
    val deg: Long,
    val gust: Double,
)
