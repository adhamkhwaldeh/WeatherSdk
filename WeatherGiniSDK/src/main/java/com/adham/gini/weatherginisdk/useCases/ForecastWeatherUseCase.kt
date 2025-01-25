package com.adham.gini.weatherginisdk.useCases

import com.adham.gini.weatherginisdk.base.BaseSealedUseCase
import com.adham.gini.weatherginisdk.base.states.BaseState
import com.adham.gini.weatherginisdk.base.states.asBasState
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import kotlinx.coroutines.CoroutineScope

/**
 * Forecast weather use case
 *
 * @property weatherGiniRepository
 * @property weatherGiniLocalRepository
 * @constructor
 *
 * @param ioScope
 */
class ForecastWeatherUseCase(
    ioScope: CoroutineScope,
    private val weatherGiniRepository: WeatherGiniRepository,
    private val weatherGiniLocalRepository: WeatherGiniLocalRepository
) : BaseSealedUseCase<ForecastResponse, ForecastWeatherUseCase.ForecastWeatherUseCaseParams>(ioScope) {
    override suspend fun run(params: ForecastWeatherUseCaseParams): BaseState<ForecastResponse> {
        return weatherGiniRepository.forecast(
            city = params.city,
            hours = params.hours,
            apiKey = weatherGiniLocalRepository.getApiKey()
        ).asBasState()
    }

    /**
     * Forecast weather use case params
     *
     * @property city
     * @property hours
     * @constructor Create empty Forecast weather use case params
     */
    data class ForecastWeatherUseCaseParams(
        val city: String,
        val hours: Int,
    )
}