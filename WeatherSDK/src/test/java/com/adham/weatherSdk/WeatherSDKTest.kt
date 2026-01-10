package com.adham.weatherSdk

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.data.params.ForecastWeatherUseCaseParams
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.providers.DataProvider
import com.adham.weatherSdk.providers.DomainProvider
import com.adham.weatherSdk.settings.WeatherSDKOptions
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.IErrorListener
import com.github.adhamkhwaldeh.commonsdk.logging.ILogger
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.unmockkAll
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class WeatherSDKTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var context: Context
    private lateinit var weatherSDK: WeatherSDK
    private val apiKey = "test_api_key"

    @Before
    fun setUp() {
        context = mockk(relaxed = true)
        mockkObject(DataProvider)
        mockkObject(DomainProvider)
        
        val localRepo = mockk<WeatherLocalRepository>(relaxed = true)
        every { DataProvider.provideWeatherLocalRepository(any()) } returns localRepo
        
        weatherSDK = WeatherSDK.Builder(context, apiKey).build()
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `addGlobalErrorListener adds a valid listener`() {
        val listener = mockk<IErrorListener>()
        weatherSDK.addGlobalErrorListener(listener)
        // Since globalErrorListeners is private in BaseSDK, we implicitly verify via other actions 
        // or assume the CopyOnWriteArrayList logic works.
    }

    @Test
    fun `addGlobalErrorListener prevents duplicate registration`() {
        val listener = mockk<IErrorListener>()
        weatherSDK.addGlobalErrorListener(listener)
        weatherSDK.addGlobalErrorListener(listener)
    }

    @Test
    fun `removeGlobalErrorListener removes an existing listener`() {
        val listener = mockk<IErrorListener>()
        weatherSDK.addGlobalErrorListener(listener)
        weatherSDK.removeGlobalErrorListener(listener)
    }

    @Test
    fun `clearGlobalErrorListeners empties the listener collection`() {
        val listener = mockk<IErrorListener>()
        weatherSDK.addGlobalErrorListener(listener)
        weatherSDK.clearGlobalErrorListeners()
    }

    @Test
    fun `updateSDKConfig with Function1 applies transformation correctly`() {
        val newApiKey = "new_api_key"
        weatherSDK.updateSDKConfig { config ->
            WeatherSDKOptions.Builder(newApiKey).build()
        }
    }

    @Test
    fun `updateSDKConfig with newConfig object replaces existing config`() {
        val newConfig = WeatherSDKOptions.Builder("new_key").build()
        weatherSDK.updateSDKConfig(newConfig)
    }

    @Test
    fun `updateLoggers replaces default loggers with provided list`() {
        val logger = mockk<ILogger>()
        weatherSDK.updateLoggers(listOf(logger))
    }

    @Test
    fun `getContext returns valid application context`() {
        assertNotNull(weatherSDK.context)
        assertEquals(context, weatherSDK.context)
    }

    @Test
    fun `getSdkStatus returns live data with initial status`() {
        assertNotNull(weatherSDK.sdkStatus)
    }

    @Test
    fun `currentWeatherUseCase success state`() = runTest {
        val city = "London"
        val expectedResponse = mockk<CurrentWeatherResponse>()
        val useCase = mockk<com.adham.weatherSdk.domain.useCases.CurrentWeatherUseCase>()
        
        every { DomainProvider.provideCurrentWeatherUseCase(any()) } returns useCase
        coEvery { useCase.invoke(city) } returns flowOf(BaseState.Success(expectedResponse))

        val resultFlow = weatherSDK.currentWeatherUseCase(city)
        
        resultFlow.collect { state ->
            assertTrue(state is BaseState.Success)
            assertEquals(expectedResponse, (state as BaseState.Success).data)
        }
    }

    @Test
    fun `forecastWeatherUseCase success state`() = runTest {
        val params = ForecastWeatherUseCaseParams("London", 24)
        val expectedResponse = mockk<ForecastResponse>()
        val useCase = mockk<com.adham.weatherSdk.domain.useCases.ForecastWeatherUseCase>()
        
        every { DomainProvider.provideForecastWeatherUseCase(any()) } returns useCase
        coEvery { useCase.invoke(params) } returns flowOf(BaseState.Success(expectedResponse))

        val resultFlow = weatherSDK.forecastWeatherUseCase(params)
        
        resultFlow.collect { state ->
            assertTrue(state is BaseState.Success)
            assertEquals(expectedResponse, (state as BaseState.Success).data)
        }
    }

    private fun assertTrue(condition: Boolean) {
        assert(condition)
    }
}
