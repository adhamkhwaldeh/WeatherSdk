package com.adham.weatherSdk.domain.models

import com.adham.weatherSdk.data.remote.dtos.weatherMap.SysWeatherMapDto
import com.adham.weatherSdk.domain.enums.WeatherMainEnum

data class CurrentWeatherMapResponseModel(
    val weather: List<WeatherMapModel>,
//    val base: String,
    val main: MainWeatherMapModel,
//    val visibility: Long?,
//    val wind: WindWeatherMapModel,
//    val rain: RainWeatherMapModel?,
//    val clouds: CloudsWeatherMapModel,
//    val dt: Long,
    val sys: SysWeatherMapDto,
    val timezone: Long,
    val id: Long,
    val name: String,
) {
    val weatherMain: WeatherMainEnum
        get() {
            return weather.firstOrNull()?.weatherMain ?: WeatherMainEnum.Sunny
        }
}
