package com.adham.weatherSdk.domain.models

import com.adham.weatherSdk.domain.enums.WeatherMainEnum

data class WeatherMapModel(
    val id: Long,
    val main: String,
    val description: String,
    val icon: String,
) {
    val weatherMain: WeatherMainEnum
        get() {
            return WeatherMainEnum.entries.firstOrNull { weather -> weather.main == main }
                ?: WeatherMainEnum.Sunny
        }
}
