package com.adham.weatherSample

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.test.core.app.ApplicationProvider
import androidx.test.platform.app.InstrumentationRegistry
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.ui.EnterCityScreen
import com.adham.weatherSample.ui.theme.WeatherSDKTheme
import com.adham.weatherSample.viewModels.WeatherViewModel
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.helpers.ConstantsHelpers
import com.adham.weatherSdk.settings.WeatherSDKOptions
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@Suppress("DEPRECATION")
class ComprehensiveInstrumentationTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val application = ApplicationProvider.getApplicationContext<Application>()

    private val mockWeatherViewModel = mockk<WeatherViewModel>(relaxed = true)

    // 1. Accessibility & UI Test
    @Test
    fun testEnterCityScreen_UIAndAccessibility() {
//        val mockSdk = WeatherSDK.Builder().build()

        composeTestRule.setContent {
            WeatherSDKTheme {
                EnterCityScreen(weatherViewModel = mockWeatherViewModel)
            }
        }

        // Verify elements are displayed
        composeTestRule
            .onNodeWithText(
                application.getString(R.string.EnterYourCityName),
            ).assertIsDisplayed()

        // Test Interaction
        composeTestRule
//            .onNodeWithText(application.getString(R.string.EnterYourCityName))
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .performTextInput("London")
        composeTestRule
            .onNodeWithText(application.getString(R.string.WeatherForecast))
            .performClick()
    }

    // 2. Integration Test: SDK Persistence
    @Test
    fun testSDKBuilder_PersistsApiKeyInRealPrefs() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        val testApiKey = "instr_test_api_key"

        // Build the SDK (this saves the key to SharedPreferences)
        WeatherSDK
            .Builder(context, testApiKey)
            .setupOptions(
                WeatherSDKOptions
                    .Builder(
                        testApiKey,
                    ).build(),
            ).build()

        // Access the actual SharedPreferences to verify persistence
        val masterKey =
            MasterKey
                .Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

        val prefs =
            EncryptedSharedPreferences.create(
                context,
                ConstantsHelpers.SHARED_PREFS_UTIL_PREFIX_TAG + "secure_prefs",
                masterKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
            )

        val savedKey = prefs.getString(ConstantsHelpers.API_KEY_TAG, null)
        assertEquals(testApiKey, savedKey)
    }
}
