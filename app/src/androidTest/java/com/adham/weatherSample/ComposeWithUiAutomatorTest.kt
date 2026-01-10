package com.adham.weatherSample

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiSelector
import androidx.test.uiautomator.Until
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComposeWithUiAutomatorTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testButtonAndHandleSystemDialog() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        // Click a Compose button
        composeTestRule.onNodeWithText("Click Me").performClick()

        // Wait and interact with a system dialog
        device.wait(Until.hasObject(By.text("Allow")), 5000)

        val allowButton = device.findObject(UiSelector().text("Allow"))
        if (allowButton.exists() && allowButton.isEnabled) {
            allowButton.click()
        }
    }
}
