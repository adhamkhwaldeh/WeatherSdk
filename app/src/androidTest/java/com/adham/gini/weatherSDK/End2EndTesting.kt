package com.adham.gini.weatherSDK

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import com.adham.gini.weatherSDK.ui.AppNavHost
import org.junit.Rule
import org.junit.Test

class End2EndTesting {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testFullWeatherAppFlow() {
        composeTestRule.setContent {
            AppNavHost()
        }

        composeTestRule.onNodeWithTag("cityInput").performTextInput("Berlin")
        composeTestRule.onNodeWithTag("searchButton").performClick()

        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithText("Weather in Berlin").assertExists()
        composeTestRule.onNodeWithTag("hourlyForecastList").assertExists()
    }
}
