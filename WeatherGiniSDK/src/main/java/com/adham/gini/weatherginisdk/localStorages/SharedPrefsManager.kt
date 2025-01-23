package com.adham.gini.weatherginisdk.localStorages


import android.content.SharedPreferences


/**
 * Shared prefs manager
 *
 * @property sharedPreferences
 * @constructor Create empty Shared prefs manager
 */
class SharedPrefsManager(private var sharedPreferences: SharedPreferences) {

    companion object {
        const val sharedPrefsUtilPrefix = "mySharedD_"
        const val apiKey = "apiKey"
    }

    /**
     * Save
     *
     * @param key
     * @param value
     */
    fun save(key: String, value: String) {
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
    fun getStringData(key: String): String? {
        val data = sharedPreferences.getString(key, null)
        return if (data != null && data.trim { it <= ' ' }.isEmpty()) {
            null
        } else data

    }

}