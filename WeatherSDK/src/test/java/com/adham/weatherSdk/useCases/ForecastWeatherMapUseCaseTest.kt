package com.adham.weatherSdk.useCases

import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel
import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherMapRepository
import com.adham.weatherSdk.domain.useCases.CurrentWeatherMapForecastUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

class ForecastWeatherMapUseCaseTest {
    private lateinit var weatherMapRepository: WeatherMapRepository
    private lateinit var weatherLocalRepository: WeatherMapLocalRepository
    private lateinit var forecastWeatherUseCase: CurrentWeatherMapForecastUseCase

    val params = AddressModel(name = "London", lat = "", lon = "")
    val apiKey = "valid_key"

    @Before
    fun setUp() {
        weatherMapRepository = mockk()
        weatherLocalRepository = mockk()
        forecastWeatherUseCase =
            CurrentWeatherMapForecastUseCase(weatherMapRepository, weatherLocalRepository)
    }

    @Test
    fun `Successful weather forecast retrieval`() =
        runTest {
            val expectedResponse = mockk<ForecastWeatherMapResponseModel>()

            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery { weatherMapRepository.forecast(params, apiKey) } returns
                flowOf(
                    BaseState.BaseStateLoadedSuccessfully(expectedResponse),
                )

            val result = forecastWeatherUseCase(params).first()

            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
            assertEquals(expectedResponse, (result as BaseState.BaseStateLoadedSuccessfully).data)
        }

    @Test(expected = UnknownHostException::class)
    fun `Remote repository network error handling`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery {
                weatherMapRepository.forecast(
                    any(),
                    any(),
                )
            } throws UnknownHostException("Network error")

            val result = forecastWeatherUseCase(params).first()

            assertTrue(result is BaseState.NoInternetError)
        }

    @Test(expected = Exception::class)
    fun `Remote repository server side error handling`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery {
                weatherMapRepository.forecast(
                    any(),
                    any(),
                )
            } throws Exception("500 Server Error")

            val result = forecastWeatherUseCase(params).first()

            assertTrue(result is BaseState.InternalServerError)
        }

    @Test(expected = IllegalArgumentException::class)
    fun `Empty city parameter validation`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery {
                weatherMapRepository.forecast(
                    params,
                    "key",
                )
            } throws IllegalArgumentException("City empty")

            val result = forecastWeatherUseCase(params).first()

            assertTrue(result is BaseState.ValidationError)
        }
}
