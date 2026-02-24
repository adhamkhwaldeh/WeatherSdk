package com.adham.weatherSdk.domain.useCases

import com.adham.weatherSdk.data.local.daos.AddressDao
import com.adham.weatherSdk.data.local.mappers.toModel
import com.adham.weatherSdk.domain.models.AddressModel
import com.github.adhamkhwaldeh.commonlibrary.base.domain.BaseUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAllSavedAddressesUseCase(
    val addressDao: AddressDao,
) : BaseUseCase<List<AddressModel>, Unit>() {
    override suspend fun invoke(params: Unit): Flow<List<AddressModel>> =
        flow {
            addressDao.loadAllDataFlow().collect {
                emit(it.map { innerIt -> innerIt.toModel() })
            }
        }
}
