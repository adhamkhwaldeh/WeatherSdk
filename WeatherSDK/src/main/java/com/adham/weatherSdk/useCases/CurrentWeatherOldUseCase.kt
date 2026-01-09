package com.adham.weatherSdk.useCases

import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.repositories.WeatherGiniLocalRepository
import com.adham.weatherSdk.repositories.WeatherGiniRepository
import com.github.adhamkhwaldeh.commonlibrary.base.BaseSealedOldUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
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
class CurrentWeatherOldUseCase(
    ioScope: CoroutineScope,
    private val weatherGiniRepository: WeatherGiniRepository,
    private val weatherGiniLocalRepository: WeatherGiniLocalRepository
) : BaseSealedOldUseCase<CurrentWeatherResponse, String>(ioScope) {

    override suspend fun run(params: String): BaseState<CurrentWeatherResponse> {
        return weatherGiniRepository.current(
            city = params,
            apiKey = weatherGiniLocalRepository.getApiKey()
        ).asBasState()
    }
}