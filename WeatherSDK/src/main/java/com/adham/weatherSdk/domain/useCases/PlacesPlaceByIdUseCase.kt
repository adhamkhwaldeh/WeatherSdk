package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.repositories.PlacesRepository
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class PlacesPlaceByIdUseCase(
    private val repository: PlacesRepository,
) : BaseSealedUseCase<GeoByNameModel, String>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: String): Flow<BaseState<GeoByNameModel>> =
        flow {
            emit(
                try {
                    repository
                        .loadPlaceDetails(
                            placeId = params,
                        ).asBasState()
                } catch (ex: Exception) {
                    BaseState.getStateByThrowable(ex)
                },
            )
        }
}
