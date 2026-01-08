package com.adham.weatherSdk

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.weatherSdk.ui.ForecastScreen
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
class ForecastScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testWeatherDisplayAndForecastList() {
        composeTestRule.setContent {
            ForecastScreen(cityName = "Berlin")//, weatherData = mockWeatherData
        }

        // Verify weather details are displayed
        composeTestRule.onNodeWithText("Weather in Berlin").assertExists()

        // Check if hourly forecast list is displayed
        composeTestRule.onNodeWithTag("hourlyForecastList").assertExists()
    }

    @Test
    fun testNoInternetShowsRetryButton() {
        composeTestRule.setContent {
            ForecastScreen(cityName = "Berlin")//, weatherData = null, isNetworkAvailable = false)
        }

        // Verify "No Connection" message appears
        composeTestRule.onNodeWithText("No Internet Connection").assertExists()

        // Verify retry button is displayed
        composeTestRule.onNodeWithTag("retryButton").assertExists()
    }

    @Test
    fun testApiFailureShowsErrorMessage() {
        composeTestRule.setContent {
            ForecastScreen(cityName = "Berlin")//, weatherData = null, isApiError = true)
        }

        // Verify error message appears
        composeTestRule.onNodeWithText("Something went wrong. Please try again.").assertExists()

        // Verify retry button is displayed
        composeTestRule.onNodeWithTag("retryButton").assertExists()
    }

    @Test
    fun testLoadingIndicatorIsDisplayed() {
        composeTestRule.setContent {
            ForecastScreen(cityName = "Berlin")// isLoading = true)
        }

        // Vérifier que l’indicateur de chargement est visible
        composeTestRule.onNodeWithTag("loadingIndicator").assertExists()
    }
}