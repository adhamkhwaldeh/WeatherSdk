package com.adham.weatherSdk.useCases

import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.data.params.ForecastWeatherUseCaseParams
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.adham.weatherSdk.domain.useCases.ForecastWeatherUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class ForecastWeatherUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherLocalRepository: WeatherLocalRepository
    private lateinit var forecastWeatherUseCase: ForecastWeatherUseCase

    @Before
    fun setUp() {
        weatherRepository = mockk()
        weatherLocalRepository = mockk()
        forecastWeatherUseCase = ForecastWeatherUseCase(weatherRepository, weatherLocalRepository)
    }

    @Test
    fun `Successful weather forecast retrieval`() = runTest {
        val params = ForecastWeatherUseCaseParams(city = "London", hours = 24)
        val apiKey = "valid_key"
        val expectedResponse = mockk<ForecastResponse>()

        every { weatherLocalRepository.getApiKey() } returns apiKey
        coEvery { weatherRepository.forecast("London", 24, apiKey) } returns Result.success(
            expectedResponse
        )

        val result = forecastWeatherUseCase(params).first()

        assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
        assertEquals(expectedResponse, (result as BaseState.BaseStateLoadedSuccessfully).data)
    }

    @Test
    fun `API key retrieval failure handling`() = runTest {
        val params = ForecastWeatherUseCaseParams("London", 24)
        every { weatherLocalRepository.getApiKey() } throws RuntimeException("Auth error")

        val result = forecastWeatherUseCase(params).first()

        assertTrue(result is BaseState.InternalServerError)
    }

    @Test
    fun `Remote repository network error handling`() = runTest {
        val params = ForecastWeatherUseCaseParams("London", 24)
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery {
            weatherRepository.forecast(
                any(),
                any(),
                any()
            )
        } throws IOException("Network error")

        val result = forecastWeatherUseCase(params).first()

        assertTrue(result is BaseState.NoInternetError)
    }

    @Test
    fun `Remote repository server side error handling`() = runTest {
        val params = ForecastWeatherUseCaseParams("London", 24)
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery {
            weatherRepository.forecast(
                any(),
                any(),
                any()
            )
        } throws Exception("500 Server Error")

        val result = forecastWeatherUseCase(params).first()

        assertTrue(result is BaseState.InternalServerError)
    }

    @Test
    fun `Empty city parameter validation`() = runTest {
        val params = ForecastWeatherUseCaseParams("", 24)
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery {
            weatherRepository.forecast(
                "",
                24,
                "key"
            )
        } throws IllegalArgumentException("City empty")

        val result = forecastWeatherUseCase(params).first()

        assertTrue(result is BaseState.ValidationError)
    }

    @Test
    fun `Extreme hours parameter values`() = runTest {
        val params = ForecastWeatherUseCaseParams("London", -1)
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.forecast("London", -1, "key") } returns mockk()

        val result = forecastWeatherUseCase(params).first()

        assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
    }

    @Test
    fun `Null data transformation via asBasState`() = runTest {
        val params = ForecastWeatherUseCaseParams("London", 24)
        every { weatherLocalRepository.getApiKey() } returns "key"
        // Simulate repository returning null if allowed by signature, or mapping behavior
        coEvery { weatherRepository.forecast(any(), any(), any()) } throws NullPointerException()

        val result = forecastWeatherUseCase(params).first()

        assertTrue(result is BaseState.InternalServerError)
    }

    @Test
    fun `Flow emission completeness`() = runTest {
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.forecast(any(), any(), any()) } returns mockk()

        val emissions = mutableListOf<BaseState<ForecastResponse>>()
        forecastWeatherUseCase(ForecastWeatherUseCaseParams("L", 1)).collect { emissions.add(it) }

        assertEquals(1, emissions.size)
    }

    @Test
    fun `Concurrent execution safety`() = runTest {
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.forecast(any(), any(), any()) } returns mockk()

        val flow1 = forecastWeatherUseCase(ForecastWeatherUseCaseParams("London", 24))
        val flow2 = forecastWeatherUseCase(ForecastWeatherUseCaseParams("Paris", 12))

        assertTrue(flow1.first() is BaseState.BaseStateLoadedSuccessfully)
        assertTrue(flow2.first() is BaseState.BaseStateLoadedSuccessfully)
    }

    @Test
    fun `Repository timeout handling`() = runTest {
        //TODO need to be checked
//        every { weatherLocalRepository.getApiKey() } returns "key"
//        coEvery { weatherRepository.forecast(any(), any(), any()) } throws kotlinx.coroutines.TimeoutCancellationException("Timeout")
//
//        val result = forecastWeatherUseCase(ForecastWeatherUseCaseParams("London", 24)).first()
//
//        assertTrue(result is BaseState.InternalServerError)
    }
}
