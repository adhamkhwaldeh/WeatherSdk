package com.adham.gini.weatherginisdk

import androidx.compose.ui.test.assertContentDescriptionEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import org.junit.Rule
import org.junit.Test

// Ensures the app is usable by people with disabilities.

class AccessibilityTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun testButtonHasContentDescription() {
        composeTestRule
            .onNodeWithTag("searchButton")
            .assertContentDescriptionEquals("Search Weather")
    }
}
