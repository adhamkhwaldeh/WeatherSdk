package com.adham.weatherSample

import android.app.Application
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.filter
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.core.app.ApplicationProvider
import com.adham.weatherSample.helpers.TestingConstantHelper
import com.adham.weatherSample.presentation.ui.AppNavHost
import com.adham.weatherSample.presentation.ui.providers.LocalWeatherThemeController
import com.adham.weatherSample.presentation.ui.providers.WeatherThemeController
import com.adham.weatherSample.presentation.ui.theme.WeatherAppTheme
import org.junit.Before
import org.junit.Rule
import org.junit.Test

// @FixMethodOrder(MethodSorters.NAME_ASCENDING)
class End2EndSuccessMapTesting {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val cityName = "Berlin"
    private val application = ApplicationProvider.getApplicationContext<Application>()

    @Before
    fun setup() {
        composeTestRule.setContent {
            WeatherAppTheme {
                val controller = remember { WeatherThemeController() }
                CompositionLocalProvider(
                    LocalWeatherThemeController provides controller,
                ) {
                    AppNavHost()
                }
            }
        }
    }

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun fullWeatherAppFlow() {
        composeTestRule
            .onNodeWithTag(TestingConstantHelper.CITY_INPUT_TAG)
            .performTextInput(cityName)

        composeTestRule.waitUntilAtLeastOneExists(
            matcher =
                hasText(cityName, substring = true).and(
                    hasTestTag(
                        TestingConstantHelper.CITY_INPUT_TAG,
                    ).not(),
                ),
            timeoutMillis = 10000,
        )

        composeTestRule
            .onAllNodes(hasText(cityName, substring = true))
            .filter(hasTestTag(TestingConstantHelper.CITY_INPUT_TAG).not())
            .onFirst()
            .performClick()

        composeTestRule
            .onNodeWithText(application.getString(R.string.WeatherForecast))
            .performClick()

        // Wait for the API call to complete and the result to be displayed
        composeTestRule.waitUntilAtLeastOneExists(
            matcher =
                hasText(
                    cityName,
                    substring = true,
                ),
            timeoutMillis = 30000,
        )

        composeTestRule
            .onNodeWithText(
                cityName,
            ).assertExists()

        composeTestRule.onNodeWithTag(TestingConstantHelper.HOURLY_FORECAST_LIST).assertExists()
    }
}
