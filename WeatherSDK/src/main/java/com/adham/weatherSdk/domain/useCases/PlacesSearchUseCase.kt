package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.domain.models.PlaceModel
import com.adham.weatherSdk.domain.repositories.PlacesRepository
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class PlacesSearchUseCase(
    private val repository: PlacesRepository,
) : BaseSealedUseCase<List<PlaceModel>, String>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: String): Flow<BaseState<List<PlaceModel>>> =
        flow {
            emit(
                try {
                    repository
                        .search(
                            query = params,
                        ).asBasState()
                } catch (ex: Exception) {
                    BaseState.getStateByThrowable(ex)
                },
            )
        }
}
