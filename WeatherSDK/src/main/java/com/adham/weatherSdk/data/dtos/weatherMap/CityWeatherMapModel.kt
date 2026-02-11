package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CityWeatherMapModel(
    val id: Long,
    val name: String,
    val coord: CoordinationWeatherMapModel,
    val country: String,
    val population: Long,
    val timezone: Long,
    val sunrise: Long,
    val sunset: Long,
)
