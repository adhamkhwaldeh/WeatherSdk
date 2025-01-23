package com.adham.gini.weatherginisdk.data.states

sealed class WeatherSdkStatus {
    object OnInitialize : WeatherSdkStatus()
    data class OnLaunchForecast(val cityName: String) : WeatherSdkStatus()
    object OnFinish : WeatherSdkStatus()
    data class OnFinishWithError(val exception: Exception?) : WeatherSdkStatus()
}
