package com.adham.weatherSdk.data.repositories

import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.localStorages.SharedPrefsManager
import com.adham.weatherSdk.localStorages.SharedPrefsManagerImpl

/**
 * Weather local repository
 *
 * @property sharedPrefsManager
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
                SharedPrefsManagerImpl.API_KEY, apiKey
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
        return sharedPrefsManager.getStringData(SharedPrefsManagerImpl.API_KEY) ?: ""
    }

}