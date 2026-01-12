package com.adham.weatherSample

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import androidx.test.platform.app.InstrumentationRegistry
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.helpers.ConstantsHelpers
import com.adham.weatherSdk.settings.WeatherSDKOptions
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

@Suppress("DEPRECATION")
class ApiKeyInstrumentationTests {
    @get:Rule
    val composeTestRule = createComposeRule()

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
