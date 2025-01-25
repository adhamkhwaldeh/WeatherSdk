package com.adham.gini.weatherginisdk

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.gini.weatherginisdk.base.states.BaseState
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
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


@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelCurrentWeatherTest : KoinTest {

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

    @Before
    fun setUp() {
        WeatherGiniSDKTestingBuilder.initialize(
            application
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin() // Stop Koin after each test
    }

    @Test
    fun test_sdk_is_not_initialized() = runTest {
        assert(localRepository.getApiKey().isBlank())
    }

    @Test
    fun test_sdk_is_initialized_successfully() = runTest {
        localRepository.saveApiKey(ConstantsHelpers.testApiKey)
        assert(localRepository.getApiKey().isNotBlank())
    }

    @Test
    fun test_load_data_successfully() = runTest {

        coEvery {
            repository.current(
                DummyDataHelper.cityName, localRepository.getApiKey()
            )
        }.returns(
            Result.success(DummyDataHelper.weatherSuccessData)
        )

        viewModel.loadCurrentWeather(DummyDataHelper.cityName)

        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        testDispatcher.scheduler.runCurrent()

        withContext(Dispatchers.IO) {
            TimeUnit.SECONDS.sleep(2)
        }

        coVerify {
            repository.current(
                DummyDataHelper.cityName, localRepository.getApiKey()
            )
        }
        assertEquals(viewModel.currentWeather.value, DummyDataHelper.weatherSuccessState)
    }

    @Test
    fun test_load_data_NoInternet() = runTest {
        coEvery {
            repository.current(
                DummyDataHelper.cityName, localRepository.getApiKey()
            )
        }.returns(
            Result.failure(DummyDataHelper.noInternetException)
        )

        viewModel.loadCurrentWeather(DummyDataHelper.cityName)

        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        testDispatcher.scheduler.runCurrent()

        withContext(Dispatchers.IO) {
            TimeUnit.SECONDS.sleep(2)
        }

        coVerify {
            repository.current(
                DummyDataHelper.cityName, localRepository.getApiKey()
            )
        }
        assert(
            viewModel.currentWeather.value is
                    BaseState.NoInternetError<CurrentWeatherResponse>
        )
    }

    @Test
    fun test_Not_Authorized() = runTest {
        coEvery {
            repository.current(
                DummyDataHelper.cityName, localRepository.getApiKey()
            )
        }.returns(
            Result.failure(DummyDataHelper.notAuthorizeException)
        )

        viewModel.loadCurrentWeather(DummyDataHelper.cityName)

        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        testDispatcher.scheduler.runCurrent()

        withContext(Dispatchers.IO) {
            TimeUnit.SECONDS.sleep(2)
        }

        coVerify {
            repository.current(
                DummyDataHelper.cityName, localRepository.getApiKey()
            )
        }
        assert(
            viewModel.currentWeather.value is
                    BaseState.NoAuthorized<CurrentWeatherResponse>
        )
    }

    @Test
    fun test_Internal_Server_Error() = runTest {
        coEvery {
            repository.current(
                DummyDataHelper.cityName, localRepository.getApiKey()
            )
        }.returns(
            Result.failure(DummyDataHelper.internalServerError)
        )

        viewModel.loadCurrentWeather(DummyDataHelper.cityName)

        advanceUntilIdle()
        testDispatcher.scheduler.advanceUntilIdle()
        testDispatcher.scheduler.runCurrent()

        withContext(Dispatchers.IO) {
            TimeUnit.SECONDS.sleep(2)
        }

        coVerify {
            repository.current(
                DummyDataHelper.cityName, localRepository.getApiKey()
            )
        }
        assert(
            viewModel.currentWeather.value is
                    BaseState.InternalServerError<CurrentWeatherResponse>
        )
    }

}
