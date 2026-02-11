package com.adham.weatherSdk.domain.repositories

import com.adham.weatherSdk.data.dtos.weatherMap.CurrentWeatherMapResponse
import com.adham.weatherSdk.data.dtos.weatherMap.ForecastWeatherMapResponse
import com.adham.weatherSdk.data.dtos.weatherMap.GeoByNameModel

interface WeatherMapRepository {
    // https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={API key}
    suspend fun current(
        lat: String,
        lon: String,
        key: String,
    ): Result<CurrentWeatherMapResponse>

    // api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
    suspend fun forecast(
        lat: String,
        lon: String,
        key: String,
    ): Result<ForecastWeatherMapResponse>

    // http://api.openweathermap.org/geo/1.0/direct?q=London&limit=5&appid={API key}
    suspend fun getGeoByName(
        q: String,
        limit: Int = 5,
        key: String,
    ): Result<List<GeoByNameModel>>

    // http://api.openweathermap.org/geo/1.0/reverse?lat={lat}&lon={lon}&limit={limit}&appid={API key}
    suspend fun getNameByGeo(
        lat: String,
        lon: String,
        limit: Int = 5,
        key: String,
    ): Result<List<GeoByNameModel>>
}
