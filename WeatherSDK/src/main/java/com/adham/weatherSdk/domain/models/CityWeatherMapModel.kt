package com.adham.weatherSdk.domain.models

data class CityWeatherMapModel(
    val id: Long,
    val name: String,
//    val coord: CoordinationWeatherMapModel,
    val country: String,
//    val population: Long,
    val timezone: Long,
//    val sunrise: Long,
//    val sunset: Long,
)
