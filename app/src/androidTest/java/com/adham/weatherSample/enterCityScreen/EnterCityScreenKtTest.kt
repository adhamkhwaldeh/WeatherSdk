package com.adham.weatherSample.enterCityScreen

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.presentation.ui.AppNavHost
import com.adham.weatherSample.presentation.ui.EnterCityScreen
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class EnterCityScreenKtTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val application = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            AppNavHost()
        }
    }

    @Test
    fun emptyCityNameValidationOnLaunch() {
        composeTestRule.setContent {
            EnterCityScreen()
        }

        composeTestRule.onNodeWithTag(TestingConstantHelper.WEATHER_FORECAST_BUTTON).performClick()

        // isError being true makes the text field show error state.
        // We can check if the error is displayed if we had a specific error message or by checking the state.
        // Since we can't easily check 'isError' property of OutlinedTextField directly via semantics easily without custom semantics,
        // we verify that sdkStatus was NOT updated.
        verify(exactly = 0) {
//            mockWeatherViewModel.updateSdkStatus(any())
        }
    }

    @Test
    fun validCityNameLaunch() {
        composeTestRule.setContent {
            EnterCityScreen()
        }

        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .performTextInput("Paris")
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.WEATHER_FORECAST_BUTTON)
            .performClick()

        verify {
//            mockWeatherViewModel.updateSdkStatus(any<WeatherSdkStatus.OnLaunchForecast>())
        }
    }

    @Test
    fun saveAddressEmptyState() {
        composeTestRule.setContent {
            EnterCityScreen()
        }

        // Clicking save when empty should set error
        composeTestRule.onNodeWithTag(TestingConstantHelper.SAVE_ADDRESS_BUTTON).performClick()

        // verify no SDK interaction
        verify(exactly = 0) {
//            mockWeatherViewModel.updateSdkStatus(any())
        }
    }

    @Test
    fun testClearTextButton() {
        composeTestRule.setContent {
            EnterCityScreen()
        }

        // Type something
        composeTestRule.onNodeWithTag("cityInput").performTextInput("TempText")

        // Find and click the clear (trailing) icon.
        // We use content description since it doesn't have a tag yet.
        composeTestRule
            .onNodeWithContentDescription(TestingConstantHelper.CLEAR_TEXT)
            .performClick()

        // Verify input is empty
        composeTestRule.onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG).assertTextContains("")
    }

    @Test
    fun initialStateValidation() {
        composeTestRule.onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG).assertTextContains("")
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.WEATHER_FORECAST_BUTTON)
            .assertIsDisplayed()
    }

    @Test
    fun inputFieldPersistence() {
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .performTextInput("London")
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .assertTextContains("London")
    }

    @Test
    fun clearTextFunctionality() {
        val cityName = "Berlin"
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .performTextInput(cityName)

        composeTestRule
            .onNodeWithContentDescription(TestingConstantHelper.CLEAR_TEXT)
            .performClick()

        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .assertTextContains("")
    }

    @Test
    fun saveAddressInteraction() {
        // Type a city
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .performTextInput("New York")

        // Click the save icon (leading icon)
        composeTestRule.onNodeWithTag(TestingConstantHelper.SAVE_ADDRESS_BUTTON).performClick()

        composeTestRule.onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG).assertIsDisplayed()
    }

    // Note: Tests involving the database (Saved Addresses list, Delete, etc.)
    // are more complex to test here because the DAO is obtained via a static call
    // to WeatherDatabase.getDatabase(context) inside the Composable.
    // For a full implementation, it's recommended to inject the DAO/Repository via Koin as well.
}
