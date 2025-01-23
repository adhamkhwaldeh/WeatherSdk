package com.adham.gini.weatherginisdk.useCases

import com.adham.gini.weatherginisdk.base.BaseSealedUseCase
import com.adham.gini.weatherginisdk.base.states.BaseState
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import kotlinx.coroutines.CoroutineScope

class ForecastWeatherUseCase(
    ioScope: CoroutineScope,
    val weatherGiniRepository: WeatherGiniRepository,
    val weatherGiniLocalRepository: WeatherGiniLocalRepository
) : BaseSealedUseCase<ForecastResponse, ForecastWeatherUseCase.ForecastWeatherUseCaseParams>(ioScope) {
    override suspend fun run(params: ForecastWeatherUseCaseParams): BaseState<ForecastResponse>? {
        return weatherGiniRepository.forecast(
            city = params.city,
            hours = params.hours,
            apiKey = weatherGiniLocalRepository.getApiKey()
        )
    }

    data class ForecastWeatherUseCaseParams(
        val city: String,
        val hours: Int,
    )
}