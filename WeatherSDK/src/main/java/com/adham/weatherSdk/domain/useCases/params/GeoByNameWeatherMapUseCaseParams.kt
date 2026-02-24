package com.adham.weatherSdk.domain.useCases.params

data class GeoByNameWeatherMapUseCaseParams(
    val q: String,
    val limit: Int = 5,
)
