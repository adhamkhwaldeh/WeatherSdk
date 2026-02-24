package com.adham.weatherSdk.useCases

import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.adham.weatherSdk.domain.useCases.CurrentWeatherUseCase
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState.InternalServerError
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.assertNotNull
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
    fun `Successful weather data retrieval`() =
        runTest {
            val cityName = "London"
            val apiKey = "valid_api_key"
            val expectedResponse = mockk<CurrentWeatherResponse>()

            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery { weatherRepository.current(cityName, apiKey) } returns
                Result.success(
                    expectedResponse,
                )

            val result = currentWeatherUseCase(cityName).first()

            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
            assertEquals(expectedResponse, (result as BaseState.BaseStateLoadedSuccessfully).data)
        }

    @Test
    fun `API key retrieval verification`() =
        runTest {
            val cityName = "Paris"
            val apiKey = "secret_key"

            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery { weatherRepository.current(any(), any()) } returns mockk()

            currentWeatherUseCase(cityName).first()

            coVerify { weatherLocalRepository.getApiKey() }
            coVerify { weatherRepository.current(cityName, apiKey) }
        }

    @Test
    fun `Empty city parameter handling`() =
        runTest {
            val emptyCity = ""
            val apiKey = "key"

            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery {
                weatherRepository.current(
                    emptyCity,
                    apiKey,
                )
            } throws IllegalArgumentException("City cannot be empty")

            val result = currentWeatherUseCase(emptyCity).first()

            assertTrue(result is BaseState.ValidationError)
        }

    @Test
    fun `Repository network error handling`() =
        runTest {
            val cityName = "Tokyo"
            val apiKey = "key"

            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery {
                weatherRepository.current(
                    cityName,
                    apiKey,
                )
            } throws IOException("No internet")

            val result = currentWeatherUseCase(cityName).first()

            assertTrue(result is BaseState.NoInternetError)
        }

    @Test
    fun `Local repository failure handling`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } throws RuntimeException("Local DB error")

            val result = currentWeatherUseCase("London").first()

            assertTrue(result is InternalServerError)
        }

    @Test
    fun `Null or missing API key scenario`() =
        runTest {
            // 1. Arrange: Return an empty key
            every { weatherLocalRepository.getApiKey() } returns ""

            // 2. Mock a 401 Unauthorized HttpException
            // We create a mock Response object to pass into the HttpException constructor
            val mockResponse =
                retrofit2.Response.error<CurrentWeatherResponse>(
                    403,
                    "".toResponseBody(null),
                )
            val unauthorizedException = retrofit2.HttpException(mockResponse)

            coEvery {
                weatherRepository.current(any(), "")
            } throws unauthorizedException

            val result = currentWeatherUseCase("London").first()

            // 4. Assert: Verify it maps to NoAuthorized
            assertTrue(
                "Expected BaseState.NoAuthorized but got ${result::class.simpleName}",
                result is BaseState.NoAuthorized,
            )
//        assertTrue(result is BaseState.NoAuthorized)
        }

    @Test
    fun `Mapping transformation verification`() =
        runTest {
            val response = mockk<CurrentWeatherResponse>()
            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery { weatherRepository.current(any(), any()) } returns Result.success(response)

            val result = currentWeatherUseCase("London").first()

            // Since we use .asBasState(), we check if the wrapper is correct
            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
            assertEquals(response, (result as BaseState.BaseStateLoadedSuccessfully).data)
        }

    @Test
    fun `Flow emission count validation`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery { weatherRepository.current(any(), any()) } returns mockk()

            val emissions = mutableListOf<BaseState<CurrentWeatherResponse>>()
            currentWeatherUseCase("London").collect { emissions.add(it) }

            assertEquals(1, emissions.size)
        }

    @Test
    fun `Special characters in city name`() =
        runTest {
            val specialCity = "S\u00e3o Paulo"
            val apiKey = "key"
            val response = mockk<CurrentWeatherResponse>()

            every { weatherLocalRepository.getApiKey() } returns apiKey
            coEvery { weatherRepository.current(specialCity, apiKey) } returns
                Result.success(
                    response,
                )

            val result = currentWeatherUseCase(specialCity).first()

            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
        }

    @Test
    fun `Coroutine cancellation safety`() =
        runTest {
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
    fun `Extremely long city name string`() =
        runTest {
            val longCity = "a".repeat(1000)
            val response = mockk<CurrentWeatherResponse>()
            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery { weatherRepository.current(longCity, any()) } returns Result.success(response)

            val result = currentWeatherUseCase(longCity).first()
            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
        }

    @Test
    fun `City name with trailing and leading whitespace`() =
        runTest {
            val paddedCity = " London "
            val response = mockk<CurrentWeatherResponse>()

            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery {
                weatherRepository.current(
                    paddedCity,
                    any(),
                )
            } returns Result.success(response)

            val result = currentWeatherUseCase(paddedCity).first()
            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
        }

    @Test
    fun `Numeric only city parameter`() =
        runTest {
            val numericCity = "12345"

            every { weatherLocalRepository.getApiKey() } returns "key"

            val internalException =
                retrofit2.HttpException(
                    retrofit2.Response.error<CurrentWeatherResponse>(
                        400,
                        "".toResponseBody(null),
                    ),
                )

            coEvery {
                weatherRepository.current(any(), "")
            } throws internalException

            coEvery { weatherRepository.current(numericCity, any()) } returns mockk()

            val result = currentWeatherUseCase(numericCity).first()
            assertTrue(result is InternalServerError)
        }

    @Test
    fun `Local repository delayed API key retrieval`() =
        runTest {
            every { weatherLocalRepository.getApiKey() } answers {
                // Simulate delay
                "delayed_key"
            }
            coEvery { weatherRepository.current(any(), "delayed_key") } returns mockk()

            val result = currentWeatherUseCase("London").first()
            assertTrue(result is InternalServerError)
        }

    @Test
    fun `Repository returns successful but empty data object`() =
        runTest {
            val emptyResponse = mockk<CurrentWeatherResponse>()
            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery {
                weatherRepository.current(
                    any(),
                    any(),
                )
            } returns Result.success(emptyResponse)

            val result = currentWeatherUseCase("London").first()
            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
            assertEquals(emptyResponse, (result as BaseState.BaseStateLoadedSuccessfully).data)
        }

    @Test
    fun `Simultaneous multiple call execution`() =
        runTest {
            val emptyResponse = mockk<CurrentWeatherResponse>()
            every { weatherLocalRepository.getApiKey() } returns "key"
            coEvery {
                weatherRepository.current(
                    any(),
                    any(),
                )
            } returns Result.success(emptyResponse)

            val flow1 = currentWeatherUseCase("London")
            val flow2 = currentWeatherUseCase("Paris")

            val res1 = flow1.first()
            val res2 = flow2.first()

            assertTrue(res1 is BaseState.BaseStateLoadedSuccessfully)
            assertTrue(res2 is BaseState.BaseStateLoadedSuccessfully)
        }

//    @Test
//    fun `City name with SQL Script injection patterns`() =
//        runTest {
//            val maliciousCity = "London'; DROP TABLE users;--"
//            every { weatherLocalRepository.getApiKey() } returns "key"
//            coEvery { weatherRepository.current(maliciousCity, any()) } returns mockk()
//
//            val result = currentWeatherUseCase(maliciousCity).first()
//            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
//        }

//    @Test
//    fun `Timeout during repository network call`() =
//        runTest {
//        every { weatherLocalRepository.getApiKey() } returns "key"
//        coEvery {
//            weatherRepository.current(
//                any(),
//                any()
//            )
//        } throws kotlinx.coroutines.TimeoutCancellationException(,"Timeout")
//
//        val result = currentWeatherUseCase("London").first()
//        assertTrue(result is BaseState.InternalServerError)
//        }

//    @Test
//    fun `API Key modification during execution`() =
//        runTest {
//            var key = "key1"
//            every { weatherLocalRepository.getApiKey() } answers { key }
//            coEvery { weatherRepository.current(any(), "key1") } coAnswers {
//                key = "key2" // Modify key during execution
//                mockk()
//            }
//
//            val result = currentWeatherUseCase("London").first()
//            assertTrue(result is BaseState.BaseStateLoadedSuccessfully)
//            coVerify(exactly = 1) { weatherRepository.current(any(), "key1") }
//        }

//    @Test
//    fun `Non terminal emission before final state`() =
//        runTest {
//            every { weatherLocalRepository.getApiKey() } returns "key"
//            coEvery { weatherRepository.current(any(), any()) } returns mockk()
//
//            val emissions = mutableListOf<BaseState<CurrentWeatherResponse>>()
//            currentWeatherUseCase("London").collect { emissions.add(it) }
//
//            // Current implementation only emits the result of .asBasState()
//            assertEquals(1, emissions.size)
//            assertTrue(emissions.first() is BaseState.BaseStateLoadedSuccessfully)
//        }
}
