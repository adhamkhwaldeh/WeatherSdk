package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ForecastWeatherMapModel(
    val dt: Long,
    val main: MainWeatherMapModel,
    val weather: List<WeatherMapModel>,
    val clouds: CloudsWeatherMapModel,
    val wind: WindWeatherMapModel,
    val visibility: Long,
    val pop: Double,
    val rain: RainWeatherMapModel?,
    val sys: SysWeatherMapModel,
    @param:Json(name = "dt_txt")
    val dtTxt: String,
)
