package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class CurrentWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val weatherLocalRepository: WeatherLocalRepository,
) : BaseSealedUseCase<CurrentWeatherResponse, String>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: String): Flow<BaseState<CurrentWeatherResponse>> =
        flow {
            emit(
                try {
                    weatherRepository
                        .current(
                            city = params,
                            apiKey = weatherLocalRepository.getApiKey(),
                        ).asBasState()
                } catch (ex: Exception) {
                    BaseState.getStateByThrowable(ex)
                },
            )
        }
}
