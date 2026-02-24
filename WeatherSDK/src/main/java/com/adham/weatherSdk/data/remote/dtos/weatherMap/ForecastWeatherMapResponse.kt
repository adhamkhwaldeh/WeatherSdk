package com.adham.weatherSdk.data.remote.dtos.weatherMap

import androidx.annotation.Keep
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ForecastWeatherMapResponse(
    val cod: String,
    val message: Long,
    val cnt: Long,
    val list: List<ForecastWeatherMapDto>,
    val city: CityWeatherMapDto,
)
