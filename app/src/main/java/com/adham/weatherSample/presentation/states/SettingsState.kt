package com.adham.weatherSample.presentation.states

import com.adham.weatherSample.domain.models.LanguageMode
import com.adham.weatherSample.domain.models.ThemeMode
import com.adham.weatherSample.domain.models.WeatherNotificationMode

data class SettingsState(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val languageMode: LanguageMode = LanguageMode.ENGLISH,
    val weatherNotificationMode: WeatherNotificationMode = WeatherNotificationMode.NEVER,
)
