package com.adham.weatherSample

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.weatherSample.viewModels.WeatherViewModel
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.domain.repositories.WeatherRepository
import com.adham.weatherSdk.helpers.ConstantsHelpers
import com.adham.weatherSdk.helpers.DummyDataHelper
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
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
 * Weather view model current weather test
 *
 * @constructor Create empty Weather view model current weather test
 */
@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelCurrentWeatherTest : KoinTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // For LiveData

    @get:Rule
    val rule = MainDispatcherRule()

    private val testDispatcher = StandardTestDispatcher()

    private val localRepository: WeatherLocalRepository by inject()

    // Please note that I'm injecting RepositoriesMockModule so the repository already mocked
    private val repository: WeatherRepository by inject()

    private val viewModel: WeatherViewModel by inject()

    private val application = ApplicationProvider.getApplicationContext<Application>()

    /**
     * Set up
     *
     */
    @Before
    fun setUp() {
        WeatherSDK.Builder().build()
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
    fun test_sdk_is_not_initialized() =
        runTest {
            assert(localRepository.getApiKey().isBlank())
        }

    /**
     * Test_sdk_is_initialized_successfully
     *
     */
    @Test
    fun test_sdk_is_initialized_successfully() =
        runTest {
            localRepository.saveApiKey(ConstantsHelpers.TEST_API_KEY)
            assert(localRepository.getApiKey().isNotBlank())
        }

    /**
     * Test_load_data_successfully
     *
     */
    @Test
    fun test_load_data_successfully() =
        runTest {
            coEvery {
                repository.current(
                    DummyDataHelper.CITY_NAME,
                    localRepository.getApiKey(),
                )
            }.returns(
                Result.success(DummyDataHelper.weatherSuccessData),
            )

            viewModel.loadCurrentWeather(DummyDataHelper.CITY_NAME)

            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()
            testDispatcher.scheduler.runCurrent()

            withContext(Dispatchers.IO) {
                TimeUnit.SECONDS.sleep(2)
            }

            coVerify {
                repository.current(
                    DummyDataHelper.CITY_NAME,
                    localRepository.getApiKey(),
                )
            }
            assertEquals(viewModel.currentWeather.value, DummyDataHelper.weatherSuccessState)
        }

    /**
     * Test_load_data_no internet
     *
     */
    @Test
    fun test_load_data_NoInternet() =
        runTest {
            coEvery {
                repository.current(
                    DummyDataHelper.CITY_NAME,
                    localRepository.getApiKey(),
                )
            }.returns(
                Result.failure(DummyDataHelper.noInternetException),
            )

            viewModel.loadCurrentWeather(DummyDataHelper.CITY_NAME)

            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()
            testDispatcher.scheduler.runCurrent()

            withContext(Dispatchers.IO) {
                TimeUnit.SECONDS.sleep(2)
            }

            coVerify {
                repository.current(
                    DummyDataHelper.CITY_NAME,
                    localRepository.getApiKey(),
                )
            }
            assert(
                viewModel.currentWeather.value is
                    BaseState.NoInternetError<CurrentWeatherResponse>,
            )
        }

    /**
     * Test_not_authorized
     *
     */
    @Test
    fun test_Not_Authorized() =
        runTest {
            coEvery {
                repository.current(
                    DummyDataHelper.CITY_NAME,
                    localRepository.getApiKey(),
                )
            }.returns(
                Result.failure(DummyDataHelper.notAuthorizeException),
            )

            viewModel.loadCurrentWeather(DummyDataHelper.CITY_NAME)

            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()
            testDispatcher.scheduler.runCurrent()

            withContext(Dispatchers.IO) {
                TimeUnit.SECONDS.sleep(2)
            }

            coVerify {
                repository.current(
                    DummyDataHelper.CITY_NAME,
                    localRepository.getApiKey(),
                )
            }
            assert(
                viewModel.currentWeather.value is
                    BaseState.NoAuthorized<CurrentWeatherResponse>,
            )
        }

    /**
     * Test_internal_server_error
     *
     */
    @Test
    fun test_Internal_Server_Error() =
        runTest {
            coEvery {
                repository.current(
                    DummyDataHelper.CITY_NAME,
                    localRepository.getApiKey(),
                )
            }.returns(
                Result.failure(DummyDataHelper.internalServerError),
            )

            viewModel.loadCurrentWeather(DummyDataHelper.CITY_NAME)

            advanceUntilIdle()
            testDispatcher.scheduler.advanceUntilIdle()
            testDispatcher.scheduler.runCurrent()

            withContext(Dispatchers.IO) {
                TimeUnit.SECONDS.sleep(2)
            }

            coVerify {
                repository.current(
                    DummyDataHelper.CITY_NAME,
                    localRepository.getApiKey(),
                )
            }
            assert(
                viewModel.currentWeather.value is
                    BaseState.InternalServerError<CurrentWeatherResponse>,
            )
        }
}
