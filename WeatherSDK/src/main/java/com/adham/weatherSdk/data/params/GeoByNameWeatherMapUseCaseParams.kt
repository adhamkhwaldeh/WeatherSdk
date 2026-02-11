package com.adham.weatherSdk.data.params

data class GeoByNameWeatherMapUseCaseParams(
    val q: String,
    val limit: Int = 5,
)
