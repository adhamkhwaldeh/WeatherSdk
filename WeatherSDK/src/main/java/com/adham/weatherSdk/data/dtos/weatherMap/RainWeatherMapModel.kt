package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RainWeatherMapModel(
    @param:Json(name = "1h")
    val n1h: Double,
)
