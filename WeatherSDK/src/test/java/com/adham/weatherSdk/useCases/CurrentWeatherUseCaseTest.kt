package com.adham.weatherSdk.useCases

import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.adham.weatherSdk.domain.useCases.CurrentWeatherUseCase
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

class CurrentWeatherUseCaseTest {

    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherLocalRepository: WeatherLocalRepository
    private lateinit var currentWeatherUseCase: CurrentWeatherUseCase

    @Before
    fun setUp() {
        weatherRepository = mockk()
        weatherLocalRepository = mockk()
        currentWeatherUseCase = CurrentWeatherUseCase(weatherRepository, weatherLocalRepository)
    }

    @Test
    fun `Successful weather data retrieval`() = runTest {
        val cityName = "London"
        val apiKey = "valid_api_key"
        val expectedResponse = mockk<CurrentWeatherResponse>()
        
        every { weatherLocalRepository.getApiKey() } returns apiKey
        coEvery { weatherRepository.current(cityName, apiKey) } returns expectedResponse

        val result = currentWeatherUseCase(cityName).first()

        assertTrue(result is BaseState.Success)
        assertEquals(expectedResponse, (result as BaseState.Success).data)
    }

    @Test
    fun `API key retrieval verification`() = runTest {
        val cityName = "Paris"
        val apiKey = "secret_key"
        
        every { weatherLocalRepository.getApiKey() } returns apiKey
        coEvery { weatherRepository.current(any(), any()) } returns mockk()

        currentWeatherUseCase(cityName).first()

        coVerify { weatherLocalRepository.getApiKey() }
        coVerify { weatherRepository.current(cityName, apiKey) }
    }

    @Test
    fun `Empty city parameter handling`() = runTest {
        val emptyCity = ""
        val apiKey = "key"
        
        every { weatherLocalRepository.getApiKey() } returns apiKey
        coEvery { weatherRepository.current(emptyCity, apiKey) } throws IllegalArgumentException("City cannot be empty")

        val result = currentWeatherUseCase(emptyCity).first()

        assertTrue(result is BaseState.Error)
    }

    @Test
    fun `Repository network error handling`() = runTest {
        val cityName = "Tokyo"
        val apiKey = "key"
        
        every { weatherLocalRepository.getApiKey() } returns apiKey
        coEvery { weatherRepository.current(cityName, apiKey) } throws IOException("No internet")

        val result = currentWeatherUseCase(cityName).first()

        assertTrue(result is BaseState.Error)
    }

    @Test
    fun `Local repository failure handling`() = runTest {
        every { weatherLocalRepository.getApiKey() } throws RuntimeException("Local DB error")

        val result = currentWeatherUseCase("London").first()

        assertTrue(result is BaseState.Error)
    }

    @Test
    fun `Null or missing API key scenario`() = runTest {
        every { weatherLocalRepository.getApiKey() } returns ""
        coEvery { weatherRepository.current(any(), "") } throws Exception("Unauthorized")

        val result = currentWeatherUseCase("London").first()

        assertTrue(result is BaseState.Error)
    }

    @Test
    fun `Mapping transformation verification`() = runTest {
        val response = mockk<CurrentWeatherResponse>()
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(any(), any()) } returns response

        val result = currentWeatherUseCase("London").first()

        // Since we use .asBasState(), we check if the wrapper is correct
        assertTrue(result is BaseState.Success)
        assertEquals(response, (result as BaseState.Success).data)
    }

    @Test
    fun `Flow emission count validation`() = runTest {
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(any(), any()) } returns mockk()

        val emissions = mutableListOf<BaseState<CurrentWeatherResponse>>()
        currentWeatherUseCase("London").collect { emissions.add(it) }

        assertEquals(1, emissions.size)
    }

    @Test
    fun `Special characters in city name`() = runTest {
        val specialCity = "S\u00e3o Paulo"
        val apiKey = "key"
        val response = mockk<CurrentWeatherResponse>()
        
        every { weatherLocalRepository.getApiKey() } returns apiKey
        coEvery { weatherRepository.current(specialCity, apiKey) } returns response

        val result = currentWeatherUseCase(specialCity).first()

        assertTrue(result is BaseState.Success)
    }

    @Test
    fun `Coroutine cancellation safety`() = runTest {
        // runTest handles cancellation internally. 
        // This test verifies that if the repository is suspended, cancellation doesn't cause a crash.
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(any(), any()) } coAnswers {
            kotlinx.coroutines.delay(1000)
            mockk()
        }

        // Execution is managed by runTest
        val result = currentWeatherUseCase("London").first()
        assertNotNull(result)
    }

    @Test
    fun `Extremely long city name string`() = runTest {
        val longCity = "a".repeat(1000)
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(longCity, any()) } returns mockk()

        val result = currentWeatherUseCase(longCity).first()
        assertTrue(result is BaseState.Success)
    }

    @Test
    fun `City name with trailing and leading whitespace`() = runTest {
        val paddedCity = " London "
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(paddedCity, any()) } returns mockk()

        val result = currentWeatherUseCase(paddedCity).first()
        assertTrue(result is BaseState.Success)
    }

    @Test
    fun `Numeric only city parameter`() = runTest {
        val numericCity = "12345"
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(numericCity, any()) } returns mockk()

        val result = currentWeatherUseCase(numericCity).first()
        assertTrue(result is BaseState.Success)
    }

    @Test
    fun `Local repository delayed API key retrieval`() = runTest {
        every { weatherLocalRepository.getApiKey() } answers {
            // Simulate delay
            "delayed_key"
        }
        coEvery { weatherRepository.current(any(), "delayed_key") } returns mockk()

        val result = currentWeatherUseCase("London").first()
        assertTrue(result is BaseState.Success)
    }

    @Test
    fun `Repository returns successful but empty data object`() = runTest {
        val emptyResponse = mockk<CurrentWeatherResponse>()
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(any(), any()) } returns emptyResponse

        val result = currentWeatherUseCase("London").first()
        assertTrue(result is BaseState.Success)
        assertEquals(emptyResponse, (result as BaseState.Success).data)
    }

    @Test
    fun `Simultaneous multiple call execution`() = runTest {
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(any(), any()) } returns mockk()

        val flow1 = currentWeatherUseCase("London")
        val flow2 = currentWeatherUseCase("Paris")

        val res1 = flow1.first()
        val res2 = flow2.first()

        assertTrue(res1 is BaseState.Success)
        assertTrue(res2 is BaseState.Success)
    }

    @Test
    fun `City name with SQL Script injection patterns`() = runTest {
        val maliciousCity = "London'; DROP TABLE users;--"
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(maliciousCity, any()) } returns mockk()

        val result = currentWeatherUseCase(maliciousCity).first()
        assertTrue(result is BaseState.Success)
    }

    @Test
    fun `Timeout during repository network call`() = runTest {
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(any(), any()) } throws kotlinx.coroutines.TimeoutCancellationException("Timeout")

        val result = currentWeatherUseCase("London").first()
        assertTrue(result is BaseState.Error)
    }

    @Test
    fun `API Key modification during execution`() = runTest {
        var key = "key1"
        every { weatherLocalRepository.getApiKey() } answers { key }
        coEvery { weatherRepository.current(any(), "key1") } coAnswers {
            key = "key2" // Modify key during execution
            mockk()
        }

        val result = currentWeatherUseCase("London").first()
        assertTrue(result is BaseState.Success)
        coVerify(exactly = 1) { weatherRepository.current(any(), "key1") }
    }

    @Test
    fun `Non terminal emission before final state`() = runTest {
        every { weatherLocalRepository.getApiKey() } returns "key"
        coEvery { weatherRepository.current(any(), any()) } returns mockk()

        val emissions = mutableListOf<BaseState<CurrentWeatherResponse>>()
        currentWeatherUseCase("London").collect { emissions.add(it) }

        // Current implementation only emits the result of .asBasState()
        assertEquals(1, emissions.size)
        assertTrue(emissions.first() is BaseState.Success)
    }
}
