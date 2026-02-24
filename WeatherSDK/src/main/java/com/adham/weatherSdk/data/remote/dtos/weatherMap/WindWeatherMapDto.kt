package com.adham.weatherSdk.data.remote.dtos.weatherMap

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class WindWeatherMapDto(
    val speed: Double,
    val deg: Long,
    val gust: Double?,
)
