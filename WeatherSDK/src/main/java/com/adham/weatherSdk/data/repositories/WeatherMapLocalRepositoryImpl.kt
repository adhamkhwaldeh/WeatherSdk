package com.adham.weatherSdk.data.repositories

import com.adham.weatherSdk.domain.repositories.WeatherMapLocalRepository
import com.adham.weatherSdk.helpers.ConstantsHelpers
import com.adham.weatherSdk.localStorages.SharedPrefsManager

class WeatherMapLocalRepositoryImpl(
    private val sharedPrefsManager: SharedPrefsManager,
) : WeatherMapLocalRepository {
    /**
     * Save api key
     *
     * @param apiKey
     */
    override fun saveApiKey(apiKey: String) {
        sharedPrefsManager.save(
            ConstantsHelpers.API_WEATHER_KEY_TAG,
            apiKey,
        )
    }

    /**
     * Get api key
     *
     * @return
     */
    override fun getApiKey(): String = sharedPrefsManager.getStringData(ConstantsHelpers.API_WEATHER_KEY_TAG) ?: ""
}
