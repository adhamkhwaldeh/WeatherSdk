package com.adham.weatherSdk.repositories

import com.adham.weatherSdk.localStorages.SharedPrefsManager

/**
 * Weather gini local repository
 *
 * @property sharedPrefsManager
 * @constructor Create empty Weather gini local repository
 */
final class WeatherGiniLocalRepository(private val sharedPrefsManager: SharedPrefsManager) {

    /**
     * Save api key
     *
     * @param apiKey
     */
    fun saveApiKey(apiKey: String) {
        try {
            sharedPrefsManager.save(
                SharedPrefsManager.apiKey, apiKey
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    /**
     * Get api key
     *
     * @return
     */
    fun getApiKey(): String {
        return sharedPrefsManager.getStringData(SharedPrefsManager.apiKey) ?: ""
    }

}