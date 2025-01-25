package com.adham.gini.weatherginisdk.useCases

import com.adham.gini.weatherginisdk.base.BaseSealedUseCase
import com.adham.gini.weatherginisdk.base.states.BaseState
import com.adham.gini.weatherginisdk.base.states.asBasState
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import kotlinx.coroutines.CoroutineScope

/**
 * Current weather use case
 *
 * @property weatherGiniRepository
 * @property weatherGiniLocalRepository
 * @constructor
 *
 * @param ioScope
 */
class CurrentWeatherUseCase(
    ioScope: CoroutineScope,
    private val weatherGiniRepository: WeatherGiniRepository,
    private val weatherGiniLocalRepository: WeatherGiniLocalRepository
) : BaseSealedUseCase<CurrentWeatherResponse, String>(ioScope) {

    override suspend fun run(params: String): BaseState<CurrentWeatherResponse> {
        return weatherGiniRepository.current(
            city = params,
            apiKey = weatherGiniLocalRepository.getApiKey()
        ).asBasState()
    }
}