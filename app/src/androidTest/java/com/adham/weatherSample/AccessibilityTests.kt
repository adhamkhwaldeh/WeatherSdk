package com.adham.weatherSample

import android.app.Application
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.adham.weatherSample.ui.EnterCityScreen
import com.adham.weatherSample.ui.theme.WeatherSDKTheme
import com.adham.weatherSample.viewModels.WeatherViewModel
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AccessibilityTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val application = ApplicationProvider.getApplicationContext<Application>()

    private val mockWeatherViewModel = mockk<WeatherViewModel>(relaxed = true)

    @Test
    fun testEnterCityScreen_TextElementsAreAccessible() {
        composeTestRule.setContent {
            WeatherSDKTheme {
                EnterCityScreen(weatherViewModel = mockWeatherViewModel)
            }
        }

        // Verify that the helper text and labels are accessible to screen readers
        composeTestRule
            .onNodeWithText(application.getString(R.string.EnterYourCityName))
            .assertIsDisplayed()
        composeTestRule
            .onNodeWithText(application.getString(R.string.WeatherForecast))
            .assertIsDisplayed()
    }
}
