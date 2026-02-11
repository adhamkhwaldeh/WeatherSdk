package com.adham.weatherSdk.data.repositories

import com.adham.weatherSdk.data.dtos.weatherMap.CurrentWeatherMapResponse
import com.adham.weatherSdk.data.dtos.weatherMap.ForecastWeatherMapResponse
import com.adham.weatherSdk.data.dtos.weatherMap.GeoByNameModel
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.adham.weatherSdk.networking.WeatherMapGeoServiceApi
import com.adham.weatherSdk.networking.WeatherMapServiceApi

internal class WeatherMapRepositoryImpl(
    private val apiService: WeatherMapServiceApi,
    private val apiGeoService: WeatherMapGeoServiceApi,
) : WeatherMapRepository {
    override suspend fun current(
        lat: String,
        lon: String,
        key: String,
    ): Result<CurrentWeatherMapResponse> = runCatching { apiService.current(lat, lon, key) }

    override suspend fun forecast(
        lat: String,
        lon: String,
        key: String,
    ): Result<ForecastWeatherMapResponse> = runCatching { apiService.forecast(lat, lon, key) }

    override suspend fun getGeoByName(
        q: String,
        limit: Int,
        key: String,
    ): Result<List<GeoByNameModel>> = runCatching { apiGeoService.getGeoByName(q = q, limit = limit, key = key) }

    override suspend fun getNameByGeo(
        lat: String,
        lon: String,
        limit: Int,
        key: String,
    ): Result<List<GeoByNameModel>> = runCatching { apiGeoService.getNameByGeo(lat = lat, lon = lon, limit = limit, key = key) }
}
