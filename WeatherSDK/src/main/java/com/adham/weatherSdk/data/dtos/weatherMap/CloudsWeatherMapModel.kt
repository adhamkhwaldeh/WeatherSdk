package com.adham.weatherSdk.data.dtos.weatherMap

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CloudsWeatherMapModel(
    val all: Long,
)
