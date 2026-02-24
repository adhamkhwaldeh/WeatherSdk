package com.adham.weatherSdk.domain.models

data class ForecastWeatherMapModel(
    val dt: Long,
    val main: MainWeatherMapModel,
    val weather: List<WeatherMapModel>,
//    val clouds: CloudsWeatherMapModel,
//    val wind: WindWeatherMapModel,
//    val visibility: Long?,
//    val pop: Double,
//    val rain: RainWeatherMapModel?,
//    val sys: SysWeatherMapModel,
    val dtTxt: String,
)
