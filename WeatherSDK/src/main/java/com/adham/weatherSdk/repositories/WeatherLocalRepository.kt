package com.adham.weatherSdk.repositories

interface WeatherLocalRepository {
    fun saveApiKey(apiKey: String)
    fun getApiKey(): String
}