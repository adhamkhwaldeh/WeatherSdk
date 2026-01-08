package com.adham.weatherSdk.useCases


import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.repositories.WeatherGiniLocalRepository
import com.adham.weatherSdk.repositories.WeatherGiniRepository
import com.github.adhamkhwaldeh.commonlibrary.base.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
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