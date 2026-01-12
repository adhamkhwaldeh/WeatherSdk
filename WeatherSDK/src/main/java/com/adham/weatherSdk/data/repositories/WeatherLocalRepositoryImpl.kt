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
internal class WeatherLocalRepositoryImpl(
    private val sharedPrefsManager: SharedPrefsManager,
) : WeatherLocalRepository {
    /**
     * Save api key
     *
     * @param apiKey
     */
    override fun saveApiKey(apiKey: String) {
        sharedPrefsManager.save(
            SharedPrefsManagerImpl.API_KEY,
            apiKey,
        )
    }

    /**
     * Get api key
     *
     * @return
     */
    override fun getApiKey(): String = sharedPrefsManager.getStringData(SharedPrefsManagerImpl.API_KEY) ?: ""
}
