package com.adham.gini.weatherginisdk.repositories

import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse

/**
 * Weather gini repository imp
 *
 * @constructor Create empty Weather gini repository imp
 */
interface WeatherGiniRepositoryImp {

    /**
     * Current
     *
     * @param city
     * @param apiKey
     * @return
     */
    suspend fun current(city: String, apiKey: String): Result<CurrentWeatherResponse>


    /**
     * Forecast
     *
     * @param city
     * @param hours
     * @param apiKey
     * @return
     */
    suspend fun forecast(city: String, hours: Int, apiKey: String): Result<ForecastResponse>

}