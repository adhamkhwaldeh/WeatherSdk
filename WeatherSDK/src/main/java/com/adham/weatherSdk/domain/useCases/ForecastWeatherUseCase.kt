package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.data.params.ForecastWeatherUseCaseParams
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class ForecastWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val weatherLocalRepository: WeatherLocalRepository,
) : BaseSealedUseCase<ForecastResponse, ForecastWeatherUseCaseParams>() {
    override suspend fun invoke(params: ForecastWeatherUseCaseParams): Flow<BaseState<ForecastResponse>> =
        flow {
            emit(
                try {
                    weatherRepository
                        .forecast(
                            city = params.city,
                            hours = params.hours,
                            apiKey = weatherLocalRepository.getApiKey(),
                        ).asBasState()
                } catch (ex: Throwable) {
                    BaseState.getStateByThrowable(ex)
                },
            )
        }
}
