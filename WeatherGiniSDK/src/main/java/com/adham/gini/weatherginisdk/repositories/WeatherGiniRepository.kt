package com.adham.gini.weatherginisdk.repositories

import com.adham.gini.weatherginisdk.networking.WeatherServiceApi
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse
import com.adham.gini.weatherginisdk.networking.WeatherMockedServiceApi

/**
 * Weather gini repository
 *
 * @property apiService
 * @constructor Create empty Weather gini repository
 */
class WeatherGiniRepository(
    private val apiService: WeatherServiceApi,
    private val mockedApiService: WeatherMockedServiceApi
) : WeatherGiniRepositoryImp {

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