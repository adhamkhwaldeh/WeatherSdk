package com.adham.weatherSdk.data.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import com.adham.weatherSdk.domain.preferences.SharedPrefsManager
import kotlin.text.isEmpty
import kotlin.text.trim

/**
 * Shared prefs manager
 *
 * @property sharedPreferences
 * @constructor Create empty Shared prefs manager
 */
internal class SharedPrefsManagerImpl(
    private var sharedPreferences: SharedPreferences,
) : SharedPrefsManager {
    /**
     * Save
     *
     * @param key
     * @param value
     */
    override fun save(
        key: String,
        value: String,
    ) {
        sharedPreferences.edit {
            SharedPreferences.Editor.putString(key, value)
        }
    }

    /**
     * Get string data
     *
     * @param key
     * @return
     */
    override fun getStringData(key: String): String? {
        val data = sharedPreferences.getString(key, null)
        return if (data != null && data.trim { it <= ' ' }.isEmpty()) {
            null
        } else {
            data
        }
    }
}
