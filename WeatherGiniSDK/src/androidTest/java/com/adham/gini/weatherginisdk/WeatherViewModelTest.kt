package com.adham.gini.weatherginisdk


import android.app.Application
import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.gini.weatherginisdk.base.states.BaseState
import com.adham.gini.weatherginisdk.data.dtos.CurrentWeatherResponse
import com.adham.gini.weatherginisdk.helpers.ConstantsHelpers
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import com.adham.gini.weatherginisdk.viewModels.WeatherViewModel
import io.mockk.CapturingSlot
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import io.mockk.slot
import io.mockk.spyk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.androidx.compose.getViewModel
import org.koin.androidx.compose.koinViewModel
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
//import org.mockito.Mockito
//import org.mockito.Mock
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
//import org.mockito.Mockito.mock
//import org.mockito.Mockito.`when`
//import org.mockito.Mockito.`when`
//import org.mockito.Mockito.mock
//import org.mockito.kotlin.*
//import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.net.UnknownHostException

//import org.junit.runners.JUnit4

@RunWith(AndroidJUnit4::class)
//@RunWith(MockitoJUnitRunner::class)
@OptIn(ExperimentalCoroutinesApi::class)
class WeatherViewModelTest : KoinTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule() // For LiveData

    private val testDispatcher = StandardTestDispatcher()

//    private val repository: WeatherGiniRepository by inject()

//    private val repository: WeatherGiniRepository = mock(
//        WeatherGiniRepository::class.java
//    )
//    val repository = mock<WeatherGiniRepository> {
////        on { getText() } doReturn "text"
//    }

    private val localRepository: WeatherGiniLocalRepository by inject()

    private val viewModel: WeatherViewModel by inject() //by lazy { viewModel() }

    val application = ApplicationProvider.getApplicationContext<Application>()

    @MockK
    lateinit var repository: WeatherGiniRepository

    val observer: Observer<BaseState<CurrentWeatherResponse>> = mockk()
    val argumentCaptor: CapturingSlot<BaseState<CurrentWeatherResponse>> = slot()
    //    @Mock
//
    @Before
    fun setUp() {
        WeatherGiniSDKBuilder.initialize(
            application,
            ConstantsHelpers.testApiKey
        )
        Dispatchers.setMain(testDispatcher)
        repository = mockk()
        viewModel.currentWeather.observeForever(observer)

    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        stopKoin() // Stop Koin after each test
        viewModel.currentWeather.removeObserver(observer)
    }

    @Test
    fun test_data_loading() = runTest {


//        // Arrange
//        Mockito.`when`(
//            repository.current(
//                "berlin", localRepository.getApiKey()
//            )
//        ).thenThrow(UnknownHostException())

//        // Arrange
//        val observer: Observer<BaseState<CurrentWeatherResponse>> = mockk()

//            spyk(object : Observer<BaseState<CurrentWeatherResponse>> {
//                override fun onChanged(value: BaseState<CurrentWeatherResponse>) {
//                    Log.v("", "")
//                }
//            })
//            mock(Observer<BaseState<CurrentWeatherResponse>>::class.java)
//        `when`(repository.getData()).thenReturn("Test Data")


        coEvery {
            repository.current(
                "berlin", localRepository.getApiKey()
            )
        }.returns(BaseState.Loading())
        //      }.throws(UnknownHostException())

        viewModel.loadCurrentWeather("berlin")

//        // Act
//        testDispatcher.scheduler.advanceUntilIdle()
//
//        // Assert
//        coVerify {
//            repository.current(
//                "berlin", localRepository.getApiKey()
//            )
//        }
//        verify { observer.onChanged(capture(mutableListOf(BaseState.NoInternetError()))) }
        verify { observer.onChanged(capture(argumentCaptor)) }

        with(argumentCaptor) {

            //Here can be assert data, example:
            assert(this.captured is BaseState.Loading<CurrentWeatherResponse>)
//            assertEquals(YourData, this.data) //validation according to your data structure
        }


    }

}
