package com.adham.weatherSample.presentation.ui.providers

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.adham.weatherSdk.domain.enums.WeatherMainEnum

internal class WeatherThemeController {
    var weatherTheme by mutableStateOf(WeatherMainEnum.Sunny)
}
