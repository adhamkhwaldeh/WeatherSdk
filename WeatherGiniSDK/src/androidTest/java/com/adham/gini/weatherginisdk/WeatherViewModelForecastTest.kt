package com.adham.gini.weatherginisdk

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.gini.weatherginisdk.base.states.BaseState
import com.adham.gini.weatherginisdk.data.dtos.ForecastResponse
import com.adham.gini.weatherginisdk.helpers.ConstantsHelpers
import com.adham.gini.weatherginisdk.helpers.DummyDataHelper
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import com.adham.gini.weatherginisdk.viewModels.WeatherViewModel
import io.mockk.coEvery
import io.mockk.coVerify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withContext
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals


/**
 * Weather view model forecast test
 *
 * @constructor Create empty Weather view model forecast test
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelForecastTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // For LiveData

    @get:Rule
    val rule = MainDispatcherRule()

    private val testDispatcher = StandardTestDispatcher()

    private val localRepository: WeatherGiniLocalRepository by inject()

    //Please note that I'm injecting RepositoriesMockModule so the repository already mocked
    private val repository: WeatherGiniRepository by inject()

    private val viewModel: WeatherViewModel by inject()

    private val application = ApplicationProvider.getApplicationContext<Application>()

    /**
     * Set up
     *
     */
    @Before
    fun setUp() {
        WeatherGiniSDKTestingBuilder.initialize(
            application
        )
        Dispatchers.setMain(testDispatcher)
    }

    /**
     * Tear down
     *
     */
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin() // Stop Koin after each test
    }

    /**
     * Test_sdk_is_not_initialized
     *
     */
    @Test
    fun test_sdk_is_not_initialized() = runTest {
        assert(localRepository.getApiKey().isBlank())
    }

    /**
     * Test_sdk_is_initialized_successfully
     *
     */
    @Test
    fun test_sdk_is_initialized_successfully() = runTest {
        localRepository.saveApiKey(ConstantsHelpers.testApiKey)
        assert(localRepository.getApiKey().isNotBlank())
    }

    /**
     * Test_load_data_successfully
     *
     */
    @Test
    fun test_load_data_successfully() = runTest {

        coEvery {
            repository.forecast(
                DummyDataHelper.cityName, DummyDataHelper.defaultHours, localRepository.getApiKey()
            )
        }.returns(
            Result.success(DummyDataHelper.forecastSuccessData)
        )

        viewModel.loadForecast(DummyDataHelper.forecastWeatherUseCaseParams)

        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        testDispatcher.scheduler.runCurrent()

        //while the use cases have suspend function the live data is not being updated instantly
        withContext(Dispatchers.IO) {
            TimeUnit.SECONDS.sleep(2)
        }

        coVerify {
            repository.forecast(
                DummyDataHelper.cityName, DummyDataHelper.defaultHours, localRepository.getApiKey()
            )
        }
        assertEquals(viewModel.forecast.value, DummyDataHelper.forecastSuccessState)
    }

    /**
     * Test_load_data_no internet
     *
     */
    @Test
    fun test_load_data_NoInternet() = runTest {
        coEvery {
            repository.forecast(
                DummyDataHelper.cityName, DummyDataHelper.defaultHours, localRepository.getApiKey()
            )
        }.returns(
            Result.failure(DummyDataHelper.noInternetException)
        )

        viewModel.loadForecast(DummyDataHelper.forecastWeatherUseCaseParams)

        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        testDispatcher.scheduler.runCurrent()

        withContext(Dispatchers.IO) {
            TimeUnit.SECONDS.sleep(2)
        }

        coVerify {
            repository.forecast(
                DummyDataHelper.cityName, DummyDataHelper.defaultHours, localRepository.getApiKey()
            )
        }
        assert(
            viewModel.forecast.value is
                    BaseState.NoInternetError<ForecastResponse>
        )
    }

    /**
     * Test_not_authorized
     *
     */
    @Test
    fun test_Not_Authorized() = runTest {

        coEvery {
            repository.forecast(
                DummyDataHelper.cityName, DummyDataHelper.defaultHours, localRepository.getApiKey()
            )
        }.returns(
            Result.failure(DummyDataHelper.notAuthorizeException)
        )

        viewModel.loadForecast(DummyDataHelper.forecastWeatherUseCaseParams)

        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        testDispatcher.scheduler.runCurrent()

        withContext(Dispatchers.IO) {
            TimeUnit.SECONDS.sleep(2)
        }

        coVerify {
            repository.forecast(
                DummyDataHelper.cityName, DummyDataHelper.defaultHours, localRepository.getApiKey()
            )
        }
        assert(
            viewModel.forecast.value is
                    BaseState.NoAuthorized<ForecastResponse>
        )
    }

    /**
     * Test_internal_server_error
     *
     */
    @Test
    fun test_Internal_Server_Error() = runTest {
        coEvery {
            repository.forecast(
                DummyDataHelper.cityName, DummyDataHelper.defaultHours, localRepository.getApiKey()
            )
        }.returns(
            Result.failure(DummyDataHelper.internalServerError)
        )

        viewModel.loadForecast(DummyDataHelper.forecastWeatherUseCaseParams)


        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        testDispatcher.scheduler.runCurrent()

        withContext(Dispatchers.IO) {
            TimeUnit.SECONDS.sleep(2)
        }

        coVerify {
            repository.forecast(
                DummyDataHelper.cityName, DummyDataHelper.defaultHours, localRepository.getApiKey()
            )
        }
        assert(
            viewModel.forecast.value is
                    BaseState.InternalServerError<ForecastResponse>
        )
    }

}
