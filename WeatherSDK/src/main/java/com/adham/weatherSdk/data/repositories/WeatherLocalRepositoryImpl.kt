package com.adham.weatherSdk.data.repositories

import com.adham.weatherSdk.domain.preferences.SharedPrefsManager
import com.adham.weatherSdk.domain.repositories.WeatherLocalRepository
import com.adham.weatherSdk.helpers.ConstantsHelpers

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
            ConstantsHelpers.API_KEY_TAG,
            apiKey,
        )
    }

    /**
     * Get api key
     *
     * @return
     */
    override fun getApiKey(): String = sharedPrefsManager.getStringData(ConstantsHelpers.API_KEY_TAG) ?: ""
}
