package com.adham.gini.weatherginisdk.repositories

import com.adham.gini.weatherginisdk.base.states.BaseState
import com.adham.gini.weatherginisdk.base.states.asBasState
import com.adham.gini.weatherginisdk.networking.WeatherService
import retrofit2.Retrofit
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse

/**
 * Weather gini repository
 *
 * @property apiService
 * @property retrofit
 * @constructor Create empty Weather gini repository
 */
class WeatherGiniRepository(
    private val apiService: WeatherService,
    private val retrofit: Retrofit
) : WeatherGiniRepositoryImp {

    override
    suspend fun current(city: String, apiKey: String): BaseState<CurrentWeatherResponse> {
        return kotlin.runCatching { apiService.current(city, apiKey) }.asBasState(retrofit)
    }

    override
    suspend fun forecast(city: String, hours: Int, apiKey: String): BaseState<ForecastResponse> {
        return kotlin.runCatching {
            apiService.forecast(
                city,
                hours,
                apiKey
            )
        }.asBasState(retrofit)
    }

}