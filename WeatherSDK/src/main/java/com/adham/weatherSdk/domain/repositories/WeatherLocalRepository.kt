package com.adham.weatherSdk.domain.repositories

interface WeatherLocalRepository {
    fun saveApiKey(apiKey: String)

    fun getApiKey(): String
}
