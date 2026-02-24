package com.adham.weatherSdk.repositories

import com.adham.weatherSdk.data.local.daos.AddressCacheDao
import com.adham.weatherSdk.data.local.entities.AddressCacheEntity
import com.adham.weatherSdk.data.local.mappers.toEntity
import com.adham.weatherSdk.data.remote.networking.WeatherMapGeoServiceApi
import com.adham.weatherSdk.data.remote.networking.WeatherMapServiceApi
import com.adham.weatherSdk.data.repositories.WeatherMapRepositoryImpl
import com.adham.weatherSdk.domain.mapper.toDto
import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.helpers.DummyDataHelper
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class WeatherMapRepositoryImplTest {
    private lateinit var repository: WeatherMapRepositoryImpl
    private val apiService = mockk<WeatherMapServiceApi>()

    private val weatherMapGeoServiceApi = mockk<WeatherMapGeoServiceApi>()

    private val addressCacheDao = mockk<AddressCacheDao>()

    private val address = AddressModel(name = "London", lat = "", lon = "")

    private val apiKey = "valid_api_key"

    @Before
    fun setup() {
        repository = WeatherMapRepositoryImpl(apiService, weatherMapGeoServiceApi, addressCacheDao)
    }

    @Test
    fun `Success case for forecast with empty cache`() =
        runTest {
            val expectedResponseModel = DummyDataHelper.forecastWeatherResponse

            coEvery {
                addressCacheDao.getAddressCache(
                    address.name,
                    address.lon,
                    address.lat,
                )
            } returns null

            coEvery {
                apiService.forecast(
                    address.lat,
                    address.lon,
                    apiKey,
                )
            } returns expectedResponseModel.toDto()

            coEvery { addressCacheDao.upsertForecastWeather(any(), any()) } just Runs

            val result = repository.forecast(address, apiKey).toList()
            assertTrue(result[0] is BaseState.Loading)
            assertTrue(result[1] is BaseState.BaseStateLoadedSuccessfully)
            val successState = result[1] as BaseState.BaseStateLoadedSuccessfully
            assertEquals(expectedResponseModel, successState.data)

            coVerify { apiService.forecast(address.lat, address.lon, apiKey) }
            coVerify {
                addressCacheDao.upsertForecastWeather(
                    address.toEntity(),
                    expectedResponseModel.toDto(),
                )
            }
            coVerify { addressCacheDao.getAddressCache(address.name, address.lon, address.lat) }
        }

    @Test
    fun `Success case for forecast with load from the cache`() =
        runTest {
            val expectedResponseModel = DummyDataHelper.forecastWeatherResponse

            val cachedData =
                AddressCacheEntity(
                    forecastWeather = expectedResponseModel.toDto(),
                )

            coEvery {
                addressCacheDao.getAddressCache(
                    address.name,
                    address.lon,
                    address.lat,
                )
            } returns cachedData

            coEvery {
                apiService.forecast(
                    address.lat,
                    address.lon,
                    apiKey,
                )
            } returns expectedResponseModel.toDto()

            coEvery { addressCacheDao.upsertForecastWeather(any(), any()) } just Runs

            val result = repository.forecast(address, apiKey).toList()
            assertTrue(result[0] is BaseState.BaseStateLoadedSuccessfully)
            assertTrue(result[1] is BaseState.Loading)
            assertTrue(result[2] is BaseState.BaseStateLoadedSuccessfully)
            val successState = result[2] as BaseState.BaseStateLoadedSuccessfully
            assertEquals(expectedResponseModel, successState.data)

            coVerify { apiService.forecast(address.lat, address.lon, apiKey) }
            coVerify {
                addressCacheDao.upsertForecastWeather(
                    address.toEntity(),
                    expectedResponseModel.toDto(),
                )
            }
            coVerify { addressCacheDao.getAddressCache(address.name, address.lon, address.lat) }
        }

    @Test
    fun `Success case for current with empty cache`() =
        runTest {
            val expectedResponseModel = DummyDataHelper.currentWeatherResponse

            coEvery {
                addressCacheDao.getAddressCache(
                    address.name,
                    address.lat,
                    address.lon,
                )
            } returns null

            coEvery {
                apiService.current(
                    address.lat,
                    address.lon,
                    apiKey,
                )
            } returns expectedResponseModel.toDto()

            coEvery { addressCacheDao.upsertCurrentWeather(any(), any()) } just Runs

            val result = repository.current(address, apiKey).toList()

            assertTrue(result[0] is BaseState.Loading)
            assertTrue(result[1] is BaseState.BaseStateLoadedSuccessfully)

            val successState = result[1] as BaseState.BaseStateLoadedSuccessfully
            assertEquals(expectedResponseModel, successState.data)

            coVerify { apiService.current(address.lat, address.lon, apiKey) }
            coVerify {
                addressCacheDao.upsertCurrentWeather(
                    address.toEntity(),
                    expectedResponseModel.toDto(),
                )
            }
            coVerify { addressCacheDao.getAddressCache(address.name, address.lon, address.lat) }
        }

    @Test
    fun `Success case for current with load from the cache`() =
        runTest {
            val expectedResponseModel = DummyDataHelper.currentWeatherResponse

            val cachedData =
                AddressCacheEntity(
                    currentWeather = expectedResponseModel.toDto(),
                )

            coEvery {
                addressCacheDao.getAddressCache(
                    address.name,
                    address.lat,
                    address.lon,
                )
            } returns cachedData

            coEvery {
                apiService.current(
                    address.lat,
                    address.lon,
                    apiKey,
                )
            } returns expectedResponseModel.toDto()

            coEvery { addressCacheDao.upsertCurrentWeather(any(), any()) } just Runs

            val result = repository.current(address, apiKey).toList()

            assertTrue(result[0] is BaseState.BaseStateLoadedSuccessfully)
            assertTrue(result[1] is BaseState.Loading)
            assertTrue(result[2] is BaseState.BaseStateLoadedSuccessfully)
            val successState = result[2] as BaseState.BaseStateLoadedSuccessfully
            assertEquals(expectedResponseModel, successState.data)

            coVerify { apiService.current(address.lat, address.lon, apiKey) }
            coVerify {
                addressCacheDao.upsertCurrentWeather(
                    address.toEntity(),
                    expectedResponseModel.toDto(),
                )
            }
            coVerify { addressCacheDao.getAddressCache(address.name, address.lon, address.lat) }
        }

    @Test
    fun `current should return failure when apiService throws exception`() =
        runBlocking {
            val exception = IOException()
            coEvery {
                addressCacheDao.getAddressCache(
                    address.name,
                    address.lon,
                    address.lat,
                )
            } returns null
            coEvery {
                apiService.current(address.lat, address.lon, apiKey)
            } throws exception
            val result = repository.current(address, apiKey).toList()
            assertTrue(result[1] is BaseState.NoInternetError)
        }
}
