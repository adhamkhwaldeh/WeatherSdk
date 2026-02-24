package com.adham.weatherSdk.data.repositories

import com.adham.weatherSdk.data.local.daos.AddressCacheDao
import com.adham.weatherSdk.data.local.mappers.toEntity
import com.adham.weatherSdk.data.remote.networking.WeatherMapGeoServiceApi
import com.adham.weatherSdk.data.remote.networking.WeatherMapServiceApi
import com.adham.weatherSdk.domain.mapper.toDto
import com.adham.weatherSdk.domain.mapper.toModel
import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.asBasState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class WeatherMapRepositoryImpl(
    private val apiService: WeatherMapServiceApi,
    private val apiGeoService: WeatherMapGeoServiceApi,
    private val addressCacheDao: AddressCacheDao,
) : WeatherMapRepository {
    override suspend fun current(
        address: AddressModel,
        key: String,
    ): Flow<BaseState<CurrentWeatherMapResponseModel>> =
        flow {
            val addressCache =
                addressCacheDao.getAddressCache(address.name, address.lon, address.lat)
            addressCache?.currentWeather?.apply {
                emit(BaseState.BaseStateLoadedSuccessfully(this.toModel()))
            }
            emit(BaseState.Loading())

            val result =
                runCatching {
                    apiService.current(address.lat, address.lon, key).toModel()
                }.asBasState()

            if (result is BaseState.BaseStateLoadedSuccessfully) {
                addressCacheDao.upsertCurrentWeather(
                    address.toEntity(),
                    result.data.toDto(),
                )
            }
            emit(result)
        }

    override suspend fun forecast(
        address: AddressModel,
        key: String,
    ): Flow<BaseState<ForecastWeatherMapResponseModel>> =
        flow {
            val addressCache =
                addressCacheDao.getAddressCache(address.name, address.lon, address.lat)
            addressCache?.forecastWeather?.apply {
                emit(BaseState.BaseStateLoadedSuccessfully(this.toModel()))
            }
            emit(BaseState.Loading())
            val result =
                runCatching {
                    apiService.forecast(address.lat, address.lon, key).toModel()
                }.asBasState()

            if (result is BaseState.BaseStateLoadedSuccessfully) {
                addressCacheDao.upsertForecastWeather(
                    address.toEntity(),
                    result.data.toDto(),
                )
            }
            emit(result)
        }

    override suspend fun getGeoByName(
        q: String,
        limit: Int,
        key: String,
    ): Result<Set<GeoByNameModel>> =
        runCatching {
            apiGeoService
                .getGeoByName(q = q, limit = limit, key = key)
                .map { it.toModel() }
                .toSet()
        }

    override suspend fun getNameByGeo(
        lat: String,
        lon: String,
        limit: Int,
        key: String,
    ): Result<Set<GeoByNameModel>> =
        runCatching {
            apiGeoService
                .getNameByGeo(
                    lat = lat,
                    lon = lon,
                    limit = limit,
                    key = key,
                ).map { it.toModel() }
                .toSet()
        }
}
