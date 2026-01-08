package com.adham.weatherSdk.data.states

/**
 * Weather sdk status
 *
 * @constructor Create empty Weather sdk status
 */
sealed class WeatherSdkStatus {
    object OnInitialize : WeatherSdkStatus()

    /**
     * On launch forecast
     *
     * @property cityName
     * @constructor Create empty On launch forecast
     */
    data class OnLaunchForecast(val cityName: String) : WeatherSdkStatus()
    object OnFinish : WeatherSdkStatus()

    /**
     * On finish with error
     *
     * @property exception
     * @constructor Create empty On finish with error
     */
    data class OnFinishWithError(val exception: Exception?) : WeatherSdkStatus()
}
