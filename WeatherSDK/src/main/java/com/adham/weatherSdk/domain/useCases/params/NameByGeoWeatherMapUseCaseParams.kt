package com.adham.weatherSdk.domain.useCases.params

data class NameByGeoWeatherMapUseCaseParams(
    val lat: String,
    val lon: String,
    val limit: Int = 5,
)
