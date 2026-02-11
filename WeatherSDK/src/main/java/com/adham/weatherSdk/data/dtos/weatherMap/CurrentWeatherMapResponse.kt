package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.JsonClass

// https://transform.tools/json-to-kotlin
@JsonClass(generateAdapter = true)
data class CurrentWeatherMapResponse(
    val coord: CoordinationWeatherMapModel,
    val weather: List<WeatherMapModel>,
    val base: String,
    val main: MainWeatherMapModel,
    val visibility: Long,
    val wind: WindWeatherMapModel,
    val rain: RainWeatherMapModel,
    val clouds: CloudsWeatherMapModel,
    val dt: Long,
    val sys: SysWeatherMapModel,
    val timezone: Long,
    val id: Long,
    val name: String,
    val cod: Long,
)
