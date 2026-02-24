package com.adham.weatherSample.domain.models

data class UserSettingsModel(
    val themeMode: ThemeMode = ThemeMode.SYSTEM,
    val languageMode: LanguageMode = LanguageMode.ENGLISH,
    val weatherNotificationMode: WeatherNotificationMode = WeatherNotificationMode.NEVER,
)
