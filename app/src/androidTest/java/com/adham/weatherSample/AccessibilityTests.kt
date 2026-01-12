package com.adham.weatherSample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import com.adham.weatherSample.ui.EnterCityScreen
import com.adham.weatherSample.ui.theme.WeatherSDKTheme
import com.adham.weatherSdk.WeatherSDK
import org.junit.Rule
import org.junit.Test

class AccessibilityTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testEnterCityScreen_TextElementsAreAccessible() {
        val mockSdk = WeatherSDK.Builder().build()

        composeTestRule.setContent {
            WeatherSDKTheme {
                EnterCityScreen(weatherSDK = mockSdk)
            }
        }

        // Verify that the helper text and labels are accessible to screen readers
        composeTestRule.onNodeWithText("Enter your city name").assertIsDisplayed()
        composeTestRule.onNodeWithText("Weather Forecast").assertIsDisplayed()
    }
}
