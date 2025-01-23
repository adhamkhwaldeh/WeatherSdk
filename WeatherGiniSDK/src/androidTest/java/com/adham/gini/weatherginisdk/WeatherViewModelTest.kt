package com.adham.gini.weatherginisdk


import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.gini.weatherginisdk.helpers.ConstantsHelpers
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.koin.test.inject
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.mockito.Mockito.*
import java.net.UnknownHostException

//import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // For LiveData

    private val testDispatcher = StandardTestDispatcher()

    private val repository: WeatherGiniRepository by inject()

    private val localRepository: WeatherGiniLocalRepository by inject()

//    private val viewModel: WeatherViewModel = WeatherViewModel(get(), get(), get())

    private val weatherGiniSDKBuilder by lazy { WeatherGiniSDKBuilder() }

    val application = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun setUp() {
        weatherGiniSDKBuilder.initialize(
            application,
//            this.getInstrumentation().getTargetContext().getApplicationContext();
            ConstantsHelpers.apiKey
        )
        Dispatchers.setMain(testDispatcher)


    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin() // Stop Koin after each test
    }

    @Test
    fun test_data_loading() = runTest {

        // Arrange
        `when`(
            repository.current(
                "berlin",
                localRepository.getApiKey()
            )
        ).thenThrow(UnknownHostException())

//        // Arrange
//        val observer: Observer<BaseState<CurrentWeatherResponse>> =
//            mock(Observer<BaseState<CurrentWeatherResponse>>::class.java)
////        `when`(repository.getData()).thenReturn("Test Data")
//
//        viewModel.currentWeather.observeForever(observer)
//
//        // Act
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        // Assert
//        verify(observer).onChanged("Test Data")
//        viewModel.currentWeather.removeObserver(observer)
    }

}
