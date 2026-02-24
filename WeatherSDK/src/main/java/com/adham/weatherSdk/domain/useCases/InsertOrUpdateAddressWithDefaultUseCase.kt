package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.data.local.daos.AddressDao
import com.adham.weatherSdk.data.local.mappers.toEntity
import com.adham.weatherSdk.data.local.mappers.toModel
import com.adham.weatherSdk.domain.models.AddressModel
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseSealedUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class InsertOrUpdateAddressWithDefaultUseCase(
    private val addressDao: AddressDao,
) : BaseSealedUseCase<AddressModel, AddressModel>() {
    @Suppress("TooGenericExceptionCaught")
    override suspend fun invoke(params: AddressModel): Flow<BaseState<AddressModel>> =
        flow {
            try {
                val result = addressDao.insertOrUpdateWithDefault(params.toEntity())
                emit(BaseState.BaseStateLoadedSuccessfully(result.toModel()))
            } catch (ex: Exception) {
                emit(BaseState.Companion.getStateByThrowable(ex))
            }
        }
}
