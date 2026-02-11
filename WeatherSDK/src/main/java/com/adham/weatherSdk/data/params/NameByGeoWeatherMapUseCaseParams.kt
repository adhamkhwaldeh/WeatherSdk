package com.adham.weatherSdk.data.params

data class NameByGeoWeatherMapUseCaseParams(
    val lat: String,
    val lon: String,
    val limit: Int = 5,
)
