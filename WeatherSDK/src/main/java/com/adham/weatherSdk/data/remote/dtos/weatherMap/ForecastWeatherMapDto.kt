package com.adham.weatherSdk.data.remote.dtos.weatherMap

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ForecastWeatherMapDto(
    val dt: Long,
    val main: MainWeatherMapDto,
    val weather: List<WeatherMapDto>,
//    val clouds: CloudsWeatherMapModel,
//    val wind: WindWeatherMapModel,
//    val visibility: Long?,
//    val pop: Double,
//    val rain: RainWeatherMapModel?,
//    val sys: SysWeatherMapModel,
    @param:Json(name = "dt_txt")
    val dtTxt: String,
)
