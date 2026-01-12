package com.adham.weatherSample

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.ui.EnterCityScreen
import com.adham.weatherSample.viewModels.WeatherViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test

// import com.adham.weatherSdk.WeatherSDK

class EnterCityScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

//    private val mockWeatherSdk = mockk<WeatherSDK>(relaxed = true)

    private val mockWeatherViewModel = mockk<WeatherViewModel>(relaxed = true)

    @Test
    fun testEnterCityAndTriggerForecast() {
        composeTestRule.setContent {
            EnterCityScreen(weatherViewModel = mockWeatherViewModel)
        }

        val cityName = "Berlin"

        // Enter city name
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .performTextInput(cityName)

        // Click weather forecast button
        composeTestRule
            .onNodeWithTag("weatherForecastButton")
            .performClick()

        // Verify that the text was entered correctly
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .assertTextContains(cityName)
    }

    @Test
    fun testClearTextButton() {
        composeTestRule.setContent {
            EnterCityScreen(weatherViewModel = mockWeatherViewModel)
        }

        // Type something
        composeTestRule.onNodeWithTag("cityInput").performTextInput("TempText")

        // Find and click the clear (trailing) icon.
        // We use content description since it doesn't have a tag yet.
        composeTestRule.onNodeWithContentDescription(TestingConstantHelper.CLEAR_TEXT).performClick()

        // Verify input is empty
        composeTestRule.onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG).assertTextContains("")
    }
}
