package com.adham.weatherSample

import android.app.Application
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
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
