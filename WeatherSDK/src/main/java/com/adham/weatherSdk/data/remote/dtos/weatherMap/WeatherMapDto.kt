package com.adham.weatherSdk.data.remote.dtos.weatherMap

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class WeatherMapDto(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
)
