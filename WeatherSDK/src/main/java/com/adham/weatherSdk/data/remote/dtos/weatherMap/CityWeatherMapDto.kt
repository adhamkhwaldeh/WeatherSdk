package com.adham.weatherSdk.data.remote.dtos.weatherMap

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class CityWeatherMapDto(
    val id: Long,
    val name: String,
//    val coord: CoordinationWeatherMapModel,
    val country: String,
//    val population: Long,
    val timezone: Long,
//    val sunrise: Long,
//    val sunset: Long,
)
