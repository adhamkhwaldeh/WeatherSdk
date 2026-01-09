package com.adham.weatherSdk.repositories

import com.adham.weatherSdk.networking.WeatherServiceApi
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.networking.BaseWeatherServiceApi

/**
 * Weather repository
 *
 * @property apiService
 * @constructor Create empty Weather repository
 */
internal class WeatherRepositoryImpl(
    private val apiService: WeatherServiceApi,
    private val mockedApiService: BaseWeatherServiceApi
) : WeatherRepository {

    // I used to return the state from the repository but I've faced an issue mocking the apiService: WeatherServiceApi
    // to make the test cases more realistic I've return Result
    override
    suspend fun current(city: String, apiKey: String): Result<CurrentWeatherResponse> {
        return kotlin.runCatching {
            if (apiKey.isBlank()) {
                mockedApiService.current(city, apiKey)
            } else {
                apiService.current(city, apiKey)
            }
        }
    }

    override
    suspend fun forecast(
        city: String,
        hours: Int,
        apiKey: String
    ): Result<ForecastResponse> {
        return kotlin.runCatching {
            if (apiKey.isBlank()) {
                mockedApiService.forecast(
                    city,
                    hours,
                    apiKey
                )
            } else {
                apiService.forecast(
                    city,
                    hours,
                    apiKey
                )
            }
        }
    }

}