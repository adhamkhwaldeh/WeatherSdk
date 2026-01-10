package com.adham.weatherSdk.useCases

import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.repositories.WeatherLocalRepository
import com.adham.weatherSdk.repositories.WeatherRepository
import com.github.adhamkhwaldeh.commonlibrary.base.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


internal class CurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val weatherLocalRepository: WeatherLocalRepository
) : BaseSealedUseCase<CurrentWeatherResponse, String>() {

    override suspend fun invoke(params: String): Flow<BaseState<CurrentWeatherResponse>> {
        return flow {
            emit(weatherRepository.current(
                city = params,
                apiKey = weatherLocalRepository.getApiKey()
            ).asBasState())
        }
    }

}