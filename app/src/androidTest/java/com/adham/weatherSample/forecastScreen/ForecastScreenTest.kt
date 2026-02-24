package com.adham.weatherSample.forecastScreen

import android.app.Application
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.weatherSample.R
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.presentation.ui.ForecastScreen
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
class ForecastScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val application = ApplicationProvider.getApplicationContext<Application>()

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun testWeatherDisplayAndForecastList() {
        val cityName = "Berlin"
        composeTestRule.setContent {
            ForecastScreen(cityName = cityName) // , weatherData = mockWeatherData
        }

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
        // Verify weather details are displayed
        composeTestRule
            .onNodeWithText(
                "${application.getString(R.string.TheWeatherIn)} $cityName ${
                    application.getString(R.string.IS)
                }",
            ).assertExists()

        // Check if hourly forecast list is displayed
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.HOURLY_FORECAST_LIST)
            .assertExists()
    }

    @Test
    fun testNoInternetShowsRetryButton() {
        composeTestRule.setContent {
            ForecastScreen(cityName = "Berlin") // , weatherData = null, isNetworkAvailable = false)
        }

        // Verify "No Connection" message appears
        composeTestRule.onNodeWithText("No Internet Connection").assertExists()

        // Verify retry button is displayed
        composeTestRule.onNodeWithTag("retryButton").assertExists()
    }

    @Test
    fun testApiFailureShowsErrorMessage() {
        composeTestRule.setContent {
            ForecastScreen(cityName = "123") // , weatherData = null, isApiError = true)
        }

        // Verify error message appears
        composeTestRule.onNodeWithText("Something went wrong. Please try again.").assertExists()

        // Verify retry button is displayed
        composeTestRule.onNodeWithTag("retryButton").assertExists()
    }

    @Test
    fun testLoadingIndicatorIsDisplayed() {
        composeTestRule.setContent {
            ForecastScreen(cityName = "Berlin") // isLoading = true)
        }

        // Vérifier que l’indicateur de chargement est visible
        composeTestRule.onNodeWithTag("loadingIndicator").assertExists()
    }
}
