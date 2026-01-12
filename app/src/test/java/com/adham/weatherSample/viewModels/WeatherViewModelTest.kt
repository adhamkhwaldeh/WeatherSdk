package com.adham.weatherSample.viewModels

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.adham.weatherSample.orm.WeatherDatabase
import com.adham.weatherSdk.WeatherSDK
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule

// import org.junit.Test
// import kotlinx.coroutines.flow.flowOf
// import kotlinx.coroutines.test.runTest
// import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
// import com.adham.weatherSdk.data.dtos.ForecastResponse
// import com.adham.weatherSdk.data.params.ForecastWeatherUseCaseParams
// import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
// import com.google.common.truth.Truth.assertThat
// import io.mockk.coEvery

@Suppress("unused")
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    private lateinit var viewModel: WeatherViewModel
    private val application: Application = mockk(relaxed = true)
    private val weatherSDK: WeatherSDK = mockk()

    private val dataBase: WeatherDatabase = mockk()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        viewModel = WeatherViewModel(application, weatherSDK, dataBase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

//    @Test
//    fun `currentWeather initial state check`() {
//        assertThat(viewModel.currentWeather.value).isInstanceOf(BaseState.Initial::class.java)
//    }

//    @Test
//    fun `loadCurrentWeather loading state transition`() =
//        runTest {
//            val city = "London"
//            coEvery { weatherSDK.currentWeatherUseCase(city) } returns flowOf()
//
//            viewModel.loadCurrentWeather(city)
//
//            assertThat(viewModel.currentWeather.value).isInstanceOf(BaseState.Loading::class.java)
//        }

//    @Test
//    fun `loadCurrentWeather success response delivery`() =
//        runTest {
//            val city = "London"
//            val mockResponse = mockk<CurrentWeatherResponse>()
//            coEvery { weatherSDK.currentWeatherUseCase(city) } returns
//                flowOf(
//                    BaseState.BaseStateLoadedSuccessfully(mockResponse),
//                )
//
//            viewModel.loadCurrentWeather(city)
//            testDispatcher.scheduler.advanceUntilIdle()
//
//            val state = viewModel.currentWeather.value
//            assertThat(state).isInstanceOf(BaseState.BaseStateLoadedSuccessfully::class.java)
//            assertThat((state as BaseState.BaseStateLoadedSuccessfully).data).isEqualTo(mockResponse)
//        }

//    @Test
//    fun `loadCurrentWeather error state handling`() =
//        runTest {
//            val city = "London"
//            val errorMessage = "Network Error"
//            coEvery { weatherSDK.currentWeatherUseCase(city) } returns
//                flowOf(
//                    BaseState.InternalServerError(errorMessage),
//                )
//
//            viewModel.loadCurrentWeather(city)
//            testDispatcher.scheduler.advanceUntilIdle()
//
//            val state = viewModel.currentWeather.value
//            assertThat(state).isInstanceOf(BaseState.InternalServerError::class.java)
//            assertThat((state as BaseState.InternalServerError).errorMessage).isEqualTo(errorMessage)
//        }

//    @Test
//    fun `forecast initial state check`() {
//        assertThat(viewModel.forecast.value).isInstanceOf(BaseState.Initial::class.java)
//    }

//    @Test
//    fun `loadForecast success response delivery`() =
//        runTest {
//            val params = ForecastWeatherUseCaseParams("London", 3)
//            val mockResponse = mockk<ForecastResponse>()
//            coEvery { weatherSDK.forecastWeatherUseCase(params) } returns
//                flowOf(
//                    BaseState.BaseStateLoadedSuccessfully(mockResponse),
//                )
//
//            viewModel.loadForecast(params)
//            testDispatcher.scheduler.advanceUntilIdle()
//
//            val state = viewModel.forecast.value
//            assertThat(state).isInstanceOf(BaseState.BaseStateLoadedSuccessfully::class.java)
//            assertThat((state as BaseState.BaseStateLoadedSuccessfully).data).isEqualTo(mockResponse)
//        }

//    @Test
//    fun `loadForecast error state handling`() =
//        runTest {
//            val params = ForecastWeatherUseCaseParams("London", 3)
//            val errorMessage = "Server Error"
//            coEvery { weatherSDK.forecastWeatherUseCase(params) } returns
//                flowOf(
//                    BaseState.InternalServerError(errorMessage),
//                )
//
//            viewModel.loadForecast(params)
//            testDispatcher.scheduler.advanceUntilIdle()
//
//            val state = viewModel.forecast.value
//            assertThat(state).isInstanceOf(BaseState.InternalServerError::class.java)
//            assertThat((state as BaseState.InternalServerError).errorMessage).isEqualTo(errorMessage)
//        }
}
