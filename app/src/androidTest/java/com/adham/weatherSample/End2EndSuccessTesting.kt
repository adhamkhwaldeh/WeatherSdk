package com.adham.weatherSample

import android.app.Application
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.ui.AppNavHost
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
class End2EndSuccessTesting {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val cityName = "Berlin"
    private val application = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            AppNavHost()
        }
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
        composeTestRule.onNodeWithTag("saveAddressButton").performClick()

        composeTestRule.onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG).assertIsDisplayed()
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun fullWeatherAppFlow() {
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .performTextInput(cityName)
        composeTestRule
            .onNodeWithText(application.getString(R.string.WeatherForecast))
            .performClick()

        // Wait for the API call to complete and the result to be displayed
        composeTestRule.waitUntilAtLeastOneExists(
            matcher =
                hasText(
                    "${application.getString(R.string.TheWeatherIn)} $cityName ${
                        application.getString(R.string.IS)
                    }",
                    substring = true,
                ),
            timeoutMillis = 30000,
        )

        composeTestRule
            .onNodeWithText(
                "${application.getString(R.string.TheWeatherIn)} $cityName ${
                    application.getString(R.string.IS)
                }",
            ).assertExists()

        composeTestRule.onNodeWithTag(TestingConstantHelper.HOURLY_FORECAST_LIST).assertExists()
    }
}
