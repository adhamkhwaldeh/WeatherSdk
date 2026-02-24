package com.adham.weatherSample.extensions

import com.adham.weatherSample.R
import com.adham.weatherSdk.domain.enums.WeatherMainEnum

fun WeatherMainEnum.getBackground(): Int =
    when (this) {
        WeatherMainEnum.Clouds -> R.drawable.forest_cloudy
        WeatherMainEnum.Sunny -> R.drawable.forest_sunny
        WeatherMainEnum.Snow, WeatherMainEnum.Rain -> R.drawable.forest_rainy
    }

fun WeatherMainEnum.getColor(): Int =
    when (this) {
        WeatherMainEnum.Clouds -> R.color.cloudy
        WeatherMainEnum.Sunny -> R.color.sunny
        WeatherMainEnum.Snow, WeatherMainEnum.Rain -> R.color.rainy
    }

fun WeatherMainEnum.getIcon(): Int =
    when (this) {
        WeatherMainEnum.Clouds -> R.drawable.ic_partly_sunny_24
        WeatherMainEnum.Sunny -> R.drawable.ic_clear_24
        WeatherMainEnum.Snow, WeatherMainEnum.Rain -> R.drawable.ic_rain_24
    }
