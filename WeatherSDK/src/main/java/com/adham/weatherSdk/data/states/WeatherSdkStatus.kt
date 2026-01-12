package com.adham.weatherSdk.data.states

/**
 * Weather sdk status
 *
 * @constructor Create empty Weather sdk status
 */
sealed class WeatherSdkStatus {
    /**
     * On launch forecast
     *
     * @property cityName
     * @constructor Create empty On launch forecast
     */
    data class OnLaunchForecast(
        val cityName: String,
    ) : WeatherSdkStatus()

    object OnFinish : WeatherSdkStatus()
}
