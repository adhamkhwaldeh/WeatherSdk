package com.adham.gini.weatherSDK

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


//Instrumentation
@RunWith(AndroidJUnit4::class)
class MainActivityInstrumentationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testNavigationToWeatherScreen() {
        composeTestRule.onNodeWithTag("cityInput").performTextInput("Berlin")
        composeTestRule.onNodeWithTag("searchButton").performClick()
        composeTestRule.onNodeWithText("Weather in Berlin").assertExists()
    }

}
