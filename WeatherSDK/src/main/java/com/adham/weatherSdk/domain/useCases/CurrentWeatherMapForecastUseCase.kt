package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.data.dtos.weatherMap.CurrentWeatherMapResponse
import com.adham.weatherSdk.data.dtos.weatherMap.ForecastWeatherMapResponse
import com.adham.weatherSdk.data.params.CurrentWeatherMapUseCaseParams
import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CurrentWeatherMapForecastUseCase(
    private val weatherRepository: WeatherMapRepository,
    private val weatherLocalRepository: WeatherMapLocalRepository,
) : BaseSealedUseCase<ForecastWeatherMapResponse, CurrentWeatherMapUseCaseParams>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: CurrentWeatherMapUseCaseParams): Flow<BaseState<ForecastWeatherMapResponse>> =
        flow {
            emit(
                try {
                    weatherRepository
                        .forecast(
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
