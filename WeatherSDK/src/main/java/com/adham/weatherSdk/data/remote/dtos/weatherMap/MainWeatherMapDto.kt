package com.adham.weatherSdk.data.remote.dtos.weatherMap

import androidx.annotation.Keep
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class MainWeatherMapDto(
    val temp: Double,
//    @param:Json(name = "feels_like")
//    val feelsLike: Double,
    @param:Json(name = "temp_min")
    val tempMin: Double,
    @param:Json(name = "temp_max")
    val tempMax: Double,
    val pressure: Long,
    val humidity: Long,
//    @param:Json(name = "sea_level")
//    val seaLevel: Long,
//    @param:Json(name = "grnd_level")
//    val grndLevel: Long,
)
