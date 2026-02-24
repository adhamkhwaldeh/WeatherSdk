package com.adham.weatherSdk.repositories

import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.data.remote.dtos.weather.ForecastResponse
import com.adham.weatherSdk.data.remote.networking.BaseWeatherServiceApi
import com.adham.weatherSdk.data.remote.networking.WeatherServiceApi
import com.adham.weatherSdk.data.repositories.WeatherRepositoryImpl
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WeatherRepositoryImplTest {
    private lateinit var repository: WeatherRepositoryImpl
    private val apiService = mockk<WeatherServiceApi>()
    private val mockedApiService = mockk<BaseWeatherServiceApi>()

    @Before
    fun setup() {
        repository = WeatherRepositoryImpl(apiService, mockedApiService)
    }

    @Test
    fun `current should call apiService when apiKey is not blank`() =
        runBlocking {
            // Arrange
            val city = "London"
            val apiKey = "valid_api_key"
            val expectedResponse = mockk<CurrentWeatherResponse>()
            coEvery { apiService.current(city, apiKey) } returns expectedResponse

            // Act
            val result = repository.current(city, apiKey)

            // Assert
            assertTrue(result.isSuccess)
            assertEquals(expectedResponse, result.getOrNull())
            coVerify { apiService.current(city, apiKey) }
            coVerify(exactly = 0) { mockedApiService.current(any(), any()) }
        }

    @Test
    fun `current should call mockedApiService when apiKey is blank`() =
        runBlocking {
            // Arrange
            val city = "London"
            val apiKey = ""
            val expectedResponse = mockk<CurrentWeatherResponse>()
            coEvery { mockedApiService.current(city, apiKey) } returns expectedResponse

            // Act
            val result = repository.current(city, apiKey)

            // Assert
            assertTrue(result.isSuccess)
            assertEquals(expectedResponse, result.getOrNull())
            coVerify { mockedApiService.current(city, apiKey) }
            coVerify(exactly = 0) { apiService.current(any(), any()) }
        }

    @Test
    fun `current should return failure when apiService throws exception`() =
        runBlocking {
            // Arrange
            val city = "London"
            val apiKey = "valid_api_key"
            val exception = Exception("Network error")
            coEvery { apiService.current(city, apiKey) } throws exception

            // Act
            val result = repository.current(city, apiKey)

            // Assert
            assertTrue(result.isFailure)
            assertEquals(exception, result.exceptionOrNull())
        }

    @Test
    fun `forecast should call apiService when apiKey is not blank`() =
        runBlocking {
            // Arrange
            val city = "London"
            val hours = 24
            val apiKey = "valid_api_key"
            val expectedResponse = mockk<ForecastResponse>()
            coEvery { apiService.forecast(city, hours, apiKey) } returns expectedResponse

            // Act
            val result = repository.forecast(city, hours, apiKey)

            // Assert
            assertTrue(result.isSuccess)
            assertEquals(expectedResponse, result.getOrNull())
            coVerify { apiService.forecast(city, hours, apiKey) }
            coVerify(exactly = 0) { mockedApiService.forecast(any(), any(), any()) }
        }

    @Test
    fun `forecast should call mockedApiService when apiKey is blank`() =
        runBlocking {
            // Arrange
            val city = "London"
            val hours = 24
            val apiKey = ""
            val expectedResponse = mockk<ForecastResponse>()
            coEvery { mockedApiService.forecast(city, hours, apiKey) } returns expectedResponse

            // Act
            val result = repository.forecast(city, hours, apiKey)

            // Assert
            assertTrue(result.isSuccess)
            assertEquals(expectedResponse, result.getOrNull())
            coVerify { mockedApiService.forecast(city, hours, apiKey) }
            coVerify(exactly = 0) { apiService.forecast(any(), any(), any()) }
        }

    @Test
    fun `forecast should return failure when apiService throws exception`() =
        runBlocking {
            // Arrange
            val city = "London"
            val hours = 24
            val apiKey = "valid_api_key"
            val exception = Exception("Network error")
            coEvery { apiService.forecast(city, hours, apiKey) } throws exception

            // Act
            val result = repository.forecast(city, hours, apiKey)

            // Assert
            assertTrue(result.isFailure)
            assertEquals(exception, result.exceptionOrNull())
        }
}
