package com.adham.weatherSample.presentation.ui.providers

import androidx.compose.runtime.compositionLocalOf

internal val LocalWeatherThemeController =
    compositionLocalOf<WeatherThemeController> {
        error("No WeatherThemeController provided")
    }
