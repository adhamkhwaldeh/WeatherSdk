package com.adham.weatherSdk.domain.models

data class ForecastWeatherMapDailyModel(
    val dtTxt: String,
    val temp: Int,
    val weather: WeatherMapModel,
)
