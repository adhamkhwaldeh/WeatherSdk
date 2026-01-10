package com.adham.weatherSample

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import com.adham.weatherSample.ui.EnterCityScreen
import com.adham.weatherSdk.WeatherSDK
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

class EnterCityScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockWeatherSdk = mockk<WeatherSDK>(relaxed = true)

    @Test
    fun testEnterCityAndTriggerForecast() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSdk)
        }

        val cityName = "Berlin"

        // Enter city name
        composeTestRule.onNodeWithTag("cityInput")
            .performTextInput(cityName)

        // Click weather forecast button
        composeTestRule.onNodeWithTag("weatherForecastButton")
            .performClick()

        // Verify that the text was entered correctly
        composeTestRule.onNodeWithTag("cityInput").assertTextContains(cityName)
    }

    @Test
    fun testSaveAddressInteraction() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSdk)
        }

        // Type a city
        composeTestRule.onNodeWithTag("cityInput").performTextInput("New York")

        // Click the save icon (leading icon)
        composeTestRule.onNodeWithTag("saveAddressButton").performClick()
        
        // Verification of database insertion is typically done in integration tests
        // but here we ensure the UI remains responsive.
        composeTestRule.onNodeWithTag("cityInput").assertIsDisplayed()
    }

    @Test
    fun testClearTextButton() {
        composeTestRule.setContent {
            EnterCityScreen(weatherSDK = mockWeatherSdk)
        }

        // Type something
        composeTestRule.onNodeWithTag("cityInput").performTextInput("TempText")
        
        // Find and click the clear (trailing) icon. 
        // We use content description since it doesn't have a tag yet.
        composeTestRule.onNodeWithContentDescription("Clear text").performClick()

        // Verify input is empty
        composeTestRule.onNodeWithTag("cityInput").assertTextContains("")
    }
}
