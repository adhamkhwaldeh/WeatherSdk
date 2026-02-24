package com.adham.weatherSdk.data.remote.dtos.weatherMap

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

// https://transform.tools/json-to-kotlin

@Keep
@JsonClass(generateAdapter = true)
data class CurrentWeatherMapResponse(
//    val coord: CoordinationWeatherMapModel,
    val weather: List<WeatherMapDto>,
//    val base: String,
    val main: MainWeatherMapDto,
//    val visibility: Long?,
//    val wind: WindWeatherMapModel,
//    val rain: RainWeatherMapModel?,
//    val clouds: CloudsWeatherMapModel,
//    val dt: Long,
    val sys: SysWeatherMapDto,
    val timezone: Long,
    val id: Long,
    val name: String,
//    val cod: Long,
)
