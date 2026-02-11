package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.weatherMap.CurrentWeatherMapResponse
import com.adham.weatherSdk.data.params.CurrentWeatherMapUseCaseParams
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrentWeatherMapUseCase(
    private val weatherRepository: WeatherMapRepository,
    private val weatherLocalRepository: WeatherMapLocalRepository,
) : BaseSealedUseCase<CurrentWeatherMapResponse, CurrentWeatherMapUseCaseParams>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: CurrentWeatherMapUseCaseParams): Flow<BaseState<CurrentWeatherMapResponse>> =
        flow {
            emit(
                try {
                    weatherRepository
                        .current(
                            lat = params.lat,
                            lon = params.lon,
                            key = weatherLocalRepository.getApiKey(),
                        ).asBasState()
                } catch (ex: Exception) {
                    BaseState.getStateByThrowable(ex)
                },
            )
        }
}
