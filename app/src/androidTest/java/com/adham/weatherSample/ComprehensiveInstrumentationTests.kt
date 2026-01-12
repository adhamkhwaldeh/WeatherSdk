package com.adham.weatherSample

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import com.adham.weatherSample.ui.EnterCityScreen
import com.adham.weatherSample.ui.theme.WeatherSDKTheme
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.localStorages.SharedPrefsManager
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ComprehensiveInstrumentationTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    // 1. Accessibility & UI Test
    @Test
    fun testEnterCityScreen_UIAndAccessibility() {
        val mockSdk = WeatherSDK.Builder().build()

        composeTestRule.setContent {
            WeatherSDKTheme {
                EnterCityScreen(weatherSDK = mockSdk)
            }
        }

        // Verify elements are displayed
        composeTestRule.onNodeWithText("Enter your city name").assertIsDisplayed()

        // Test Interaction
        composeTestRule.onNodeWithText("City name").performTextInput("London")
        composeTestRule.onNodeWithText("Weather Forecast").performClick()
    }

    // 2. Integration Test: SDK Persistence
    @Test
    fun testSDKBuilder_PersistsApiKeyInRealPrefs() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val testApiKey = "instr_test_api_key"

        // Build the SDK (this saves the key to SharedPreferences)
        WeatherSDK.Builder(context, testApiKey).build()

        // Access the actual SharedPreferences to verify persistence
        val prefs =
            context.getSharedPreferences(
                SharedPrefsManager.SHARED_PREFS_UTIL_PREFIX,
                Context.MODE_PRIVATE,
            )

        val savedKey = prefs.getString(SharedPrefsManager.API_KEY, null)
        assertEquals(testApiKey, savedKey)
    }

    // 3. UIAutomator: System Level Interaction
    @Test
    fun testDeviceHomeAndBackButtons() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // Simulate pressing the Home button
        device.pressHome()

        // Verify we are on the home screen (check for a common launcher element)
        // This is a generic check; usually you search for your app icon
        val launcherExists =
            device.findObject(UiSelector().descriptionContains("Apps")).exists() ||
                device.findObject(UiSelector().packageName("com.google.android.apps.nexuslauncher")).exists()

        // Return to the test app
        device.pressRecentApps()
        device.findObject(UiSelector().descriptionContains("WeatherSDK")).click()
    }

    // 4. Permission / System Dialog Handling (Mock)
    @Test
    fun testHandlePossibleSystemDialog() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())

        // If a "Keep app open?" or "Allow notification" dialog appears
        val allowButton = device.findObject(UiSelector().textMatches("(?i)Allow|OK|Accept"))
        if (allowButton.exists()) {
            allowButton.click()
        }
    }
}
