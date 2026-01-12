package com.adham.weatherSample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.MutableLiveData
import com.adham.weatherSample.ui.EnterCityScreen
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class EnterCityScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockWeatherSDK = mockk<WeatherSDK>(relaxed = true)
    private val sdkStatus = MutableLiveData<WeatherSdkStatus>()

    @Before
    fun setup() {
        stopKoin()
        every { mockWeatherSDK.sdkStatus } returns sdkStatus
        startKoin {
            modules(
                module {
                    single { mockWeatherSDK }
                },
            )
        }
    }

    @Test
    fun initialStateValidation() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSDK)
        }

        composeTestRule.onNodeWithTag("cityInput").assertTextContains("")
        composeTestRule.onNodeWithTag("weatherForecastButton").assertIsDisplayed()
    }

    @Test
    fun inputFieldPersistence() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSDK)
        }

        val input = composeTestRule.onNodeWithTag("cityInput")
        input.performTextInput("London")
        input.assertTextContains("London")
    }

    @Test
    fun emptyCityNameValidationOnLaunch() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSDK)
        }

        composeTestRule.onNodeWithTag("weatherForecastButton").performClick()

        // isError being true makes the text field show error state.
        // We can check if the error is displayed if we had a specific error message or by checking the state.
        // Since we can't easily check 'isError' property of OutlinedTextField directly via semantics easily without custom semantics,
        // we verify that sdkStatus was NOT updated.
        verify(exactly = 0) { mockWeatherSDK.sdkStatus.value = any() }
    }

    @Test
    fun validCityNameLaunch() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSDK)
        }

        composeTestRule.onNodeWithTag("cityInput").performTextInput("Paris")
        composeTestRule.onNodeWithTag("weatherForecastButton").performClick()

        verify { mockWeatherSDK.sdkStatus.value = any<WeatherSdkStatus.OnLaunchForecast>() }
    }

    @Test
    fun clearTextFunctionality() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSDK)
        }

        val input = composeTestRule.onNodeWithTag("cityInput")
        input.performTextInput("Berlin")

        composeTestRule.onNodeWithContentDescription("Clear text").performClick()
        input.assertTextContains("")
    }

    @Test
    fun saveAddressEmptyState() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSDK)
        }

        // Clicking save when empty should set error
        composeTestRule.onNodeWithTag("saveAddressButton").performClick()

        // verify no SDK interaction
        verify(exactly = 0) { mockWeatherSDK.sdkStatus.value = any() }
    }

    // Note: Tests involving the database (Saved Addresses list, Delete, etc.)
    // are more complex to test here because the DAO is obtained via a static call
    // to WeatherDatabase.getDatabase(context) inside the Composable.
    // For a full implementation, it's recommended to inject the DAO/Repository via Koin as well.
}
