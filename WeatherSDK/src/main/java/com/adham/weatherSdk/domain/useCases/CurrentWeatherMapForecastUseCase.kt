package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel
import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.flow.Flow

internal class CurrentWeatherMapForecastUseCase(
    private val weatherRepository: WeatherMapRepository,
    private val weatherLocalRepository: WeatherMapLocalRepository,
) : BaseSealedUseCase<ForecastWeatherMapResponseModel, AddressModel>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: AddressModel): Flow<BaseState<ForecastWeatherMapResponseModel>> =
        weatherRepository
            .forecast(
                address = params,
                key = weatherLocalRepository.getApiKey(),
            )
//        flow {
//            emit(
//                try {
//                    weatherRepository
//                        .forecast(
//                            lat = params.lat,
//                            lon = params.lon,
//                            key = weatherLocalRepository.getApiKey(),
//                        ).asBasState()
//                } catch (ex: Exception) {
//                    BaseState.getStateByThrowable(ex)
//                },
//            )
//        }
}
