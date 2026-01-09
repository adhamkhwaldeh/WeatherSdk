package com.adham.weatherSdk.useCases


import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.repositories.WeatherLocalRepository
import com.adham.weatherSdk.repositories.WeatherRepository
import com.github.adhamkhwaldeh.commonlibrary.base.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ForecastWeatherUseCase(
    private val weatherRepository: WeatherRepository,
    private val weatherLocalRepository: WeatherLocalRepository
) : BaseSealedUseCase<ForecastResponse, ForecastWeatherUseCase.ForecastWeatherUseCaseParams>() {

    override suspend fun invoke(params: ForecastWeatherUseCaseParams): Flow<BaseState<ForecastResponse>> {
        return flow {
            emit(
                weatherRepository.forecast(
                    city = params.city,
                    hours = params.hours,
                    apiKey = weatherLocalRepository.getApiKey()
                ).asBasState()
            )
        }
    }
    data class ForecastWeatherUseCaseParams(
        val city: String,
        val hours: Int,
    )

}