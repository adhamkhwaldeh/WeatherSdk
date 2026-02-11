package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.data.dtos.weatherMap.GeoByNameModel
import com.adham.weatherSdk.data.params.GeoByNameWeatherMapUseCaseParams
import com.adham.weatherSdk.data.params.NameByGeoWeatherMapUseCaseParams
import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NameByGeoWeatherMapUseCase(
    private val weatherRepository: WeatherMapRepository,
    private val weatherLocalRepository: WeatherMapLocalRepository,
) : BaseSealedUseCase<List<GeoByNameModel>, NameByGeoWeatherMapUseCaseParams>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: NameByGeoWeatherMapUseCaseParams): Flow<BaseState<List<GeoByNameModel>>> =
        flow {
            emit(
                try {
                    weatherRepository
                        .getNameByGeo(
                            lat = params.lat,
                            lon = params.lon,
                            limit = params.limit,
                            key = weatherLocalRepository.getApiKey(),
                        ).asBasState()
                } catch (ex: Exception) {
                    BaseState.getStateByThrowable(ex)
                },
            )
        }
}
