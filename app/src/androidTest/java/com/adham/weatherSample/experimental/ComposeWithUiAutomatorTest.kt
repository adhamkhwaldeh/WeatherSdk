package com.adham.weatherSample.experimental

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComposeWithUiAutomatorTest {
//    @get:Rule
//    val composeTestRule = createAndroidComposeRule<MainActivity>()
//
//    @Test
//    fun testButtonAndHandleSystemDialog() {
//        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//        // Click a Compose button
//        composeTestRule.onNodeWithText("Click Me").performClick()
//
//        // Wait and interact with a system dialog
//        device.wait(Until.hasObject(By.text("Allow")), 5000)
//
//        val allowButton = device.findObject(UiSelector().text("Allow"))
//        if (allowButton.exists() && allowButton.isEnabled) {
//            allowButton.click()
//        }
//    }

//    // 3. UIAutomator: System Level Interaction
//    @Test
//    fun testDeviceHomeAndBackButtons() {
//        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//
//        // Simulate pressing the Home button
//        device.pressHome()
//
//        // Verify we are on the home screen (check for a common launcher element)
//        // This is a generic check; usually you search for your app icon
//        val launcherExists =
//            device.findObject(UiSelector().descriptionContains("Apps")).exists() ||
//                device
//                    .findObject(UiSelector().packageName("com.google.android.apps.nexuslauncher"))
//                    .exists()
//
//        // Return to the test app
//        device.pressRecentApps()
//        device.findObject(UiSelector().descriptionContains("WeatherSDK")).click()
//    }
//
//    // 4. Permission / System Dialog Handling (Mock)
//    @Test
//    fun testHandlePossibleSystemDialog() {
//        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
//
//        // If a "Keep app open?" or "Allow notification" dialog appears
//        val allowButton = device.findObject(UiSelector().textMatches("(?i)Allow|OK|Accept"))
//        if (allowButton.exists()) {
//            allowButton.click()
//        }
//    }
}
