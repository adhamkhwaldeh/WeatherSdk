package com.adham.gini.weatherSDK

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.gini.weatherSDK.ui.AppNavHost
import com.adham.gini.weatherSDK.ui.EnterCityScreen
import org.junit.Rule
import org.junit.runner.RunWith
import kotlin.test.Test

@RunWith(AndroidJUnit4::class)
class EnterCityScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testButtonClick() {
        composeTestRule.setContent {
            EnterCityScreen()
        }

        // Enter a city name
        composeTestRule.onNodeWithTag("cityInput").performTextInput("Berlin")

        // Click the button to navigate
        composeTestRule.onNodeWithTag("searchButton").performClick()

        // Verify navigation to the weather screen
        composeTestRule.onNodeWithText("Weather in Berlin").assertExists()
        // Find the button by its text and perform a click
        composeTestRule.onNodeWithText("Click Me").performClick()

        // Verify that the UI updates correctly
        composeTestRule.onNodeWithText("Clicked!").assertExists()
    }

    @Test
    fun testEmptyCityInputShowsError() {
        composeTestRule.setContent {
            AppNavHost()
        }

        // Click the button without entering a city
        composeTestRule.onNodeWithTag("searchButton").performClick()

        // Verify error message appears
        composeTestRule.onNodeWithText("Please enter a city name").assertExists()
    }
}
