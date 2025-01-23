package com.adham.gini.weatherginisdk.repositories

import com.adham.gini.weatherginisdk.base.states.BaseState
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse

interface WeatherGiniRepositoryImp {

    suspend fun current(city: String, apiKey: String): BaseState<CurrentWeatherResponse>

    suspend fun forecast(city: String, hours: Int, apiKey: String): BaseState<ForecastResponse>
}