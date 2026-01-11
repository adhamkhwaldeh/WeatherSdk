package com.adham.weatherSdk.localStorages


import android.content.SharedPreferences


/**
 * Shared prefs manager
 *
 * @property sharedPreferences
 * @constructor Create empty Shared prefs manager
 */
internal class SharedPrefsManagerImpl(private var sharedPreferences: SharedPreferences) :
    SharedPrefsManager {


    /**
     * Save
     *
     * @param key
     * @param value
     */
    override fun save(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
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
        } else data

    }

    companion object {
        const val SHARED_PREFS_UTIL_PREFIX = "mySharedD_"
        const val API_KEY = "apiKey"
    }


}