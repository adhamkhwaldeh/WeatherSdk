package com.adham.weatherSdk.useCases

import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.adham.weatherSdk.domain.useCases.CurrentWeatherMapUseCase
import com.adham.weatherSdk.helpers.DummyDataHelper
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException
import kotlin.test.assertEquals

class CurrentWeatherMapUseCaseTest {
    private lateinit var weatherRepository: WeatherMapRepository
    private lateinit var weatherLocalRepository: WeatherMapLocalRepository
    private lateinit var currentWeatherMapUseCase: CurrentWeatherMapUseCase

    val address = DummyDataHelper.address
    val apiKey = DummyDataHelper.API_KEY

    @Before
    fun setUp() {
        weatherRepository = mockk()
        weatherLocalRepository = mockk()
        currentWeatherMapUseCase =
            CurrentWeatherMapUseCase(weatherRepository, weatherLocalRepository)
    }

    @Test
    fun `Successful weather data retrieval`() =
        runTest {
            val expectedResponse = DummyDataHelper.currentWeatherResponse

            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery { weatherRepository.current(address, apiKey) } returns
                flow {
                    emit(BaseState.Loading())
                    emit(BaseState.BaseStateLoadedSuccessfully(expectedResponse))
                }

            val result = currentWeatherMapUseCase(address).toList()

            assertTrue(result[1] is BaseState.BaseStateLoadedSuccessfully)
            assertEquals(
                expectedResponse,
                (result[1] as BaseState.BaseStateLoadedSuccessfully).data,
            )
        }

    @Test
    fun `API key retrieval verification`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery { weatherRepository.current(address, apiKey) } returns
                flow {
                    emit(BaseState.Loading())
                    emit(BaseState.BaseStateLoadedSuccessfully(DummyDataHelper.currentWeatherResponse))
                }

            val response = currentWeatherMapUseCase(address).toList()
            assert(response.size == 2)

            coVerify { weatherLocalRepository.getApiKey() }
            coVerify { weatherRepository.current(address, apiKey) }
        }

    @Test(expected = IllegalArgumentException::class)
    fun `Empty city parameter handling`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery {
                weatherRepository.current(
                    address,
                    apiKey,
                )
            } throws IllegalArgumentException("City cannot be empty")

            val result = currentWeatherMapUseCase(address).first()

            assertTrue(result is BaseState.ValidationError)
        }

    @Test(expected = IOException::class)
    fun `Repository network error handling`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery {
                weatherRepository.current(
                    address,
                    apiKey,
                )
            } throws IOException("No internet")

            val result = currentWeatherMapUseCase(address).first()

            assertTrue(result is BaseState.NoInternetError)
        }
}
