package com.adham.weatherSdk.domain.models

data class MainWeatherMapModel(
    val temp: Double,
//    @param:Json(name = "feels_like")
//    val feelsLike: Double,
    val tempMin: Double,
    val tempMax: Double,
    val pressure: Long,
    val humidity: Long,
//    @param:Json(name = "sea_level")
//    val seaLevel: Long,
//    @param:Json(name = "grnd_level")
//    val grndLevel: Long,
)
