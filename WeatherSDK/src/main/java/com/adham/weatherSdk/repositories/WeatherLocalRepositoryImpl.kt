package com.adham.weatherSdk.repositories

import com.adham.weatherSdk.localStorages.SharedPrefsManager
import com.adham.weatherSdk.localStorages.SharedPrefsManagerImpl

/**
 * Weather local repository
 *
 * @property sharedPrefsManagerImpl
 * @constructor Create empty Weather local repository
 */
internal class WeatherLocalRepositoryImpl(private val sharedPrefsManager: SharedPrefsManager):
    WeatherLocalRepository {

    /**
     * Save api key
     *
     * @param apiKey
     */
   override fun saveApiKey(apiKey: String) {
        try {
            sharedPrefsManager.save(
                SharedPrefsManagerImpl.apiKey, apiKey
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
    override fun getApiKey(): String {
        return sharedPrefsManager.getStringData(SharedPrefsManagerImpl.apiKey) ?: ""
    }

}