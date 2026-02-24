package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.adham.weatherSdk.domain.useCases.params.NameByGeoWeatherMapUseCaseParams
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NameByGeoWeatherMapUseCase(
    private val weatherRepository: WeatherMapRepository,
    private val weatherLocalRepository: WeatherMapLocalRepository,
) : BaseSealedUseCase<Set<GeoByNameModel>, NameByGeoWeatherMapUseCaseParams>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: NameByGeoWeatherMapUseCaseParams): Flow<BaseState<Set<GeoByNameModel>>> =
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
