package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastWeatherMapResponse(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ForecastWeatherMapModel>,
    val city: CityWeatherMapModel,
)
