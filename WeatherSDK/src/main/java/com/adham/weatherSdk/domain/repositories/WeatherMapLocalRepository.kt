package com.adham.weatherSdk.domain.repositories

interface WeatherMapLocalRepository {
    fun saveApiKey(apiKey: String)

    fun getApiKey(): String
}
