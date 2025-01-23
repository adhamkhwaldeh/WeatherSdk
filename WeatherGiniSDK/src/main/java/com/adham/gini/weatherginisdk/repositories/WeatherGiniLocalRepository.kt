package com.adham.gini.weatherginisdk.repositories

import com.adham.gini.weatherginisdk.localStorages.SharedPrefsManager

class WeatherGiniLocalRepository(private val sharedPrefsManager: SharedPrefsManager) {


    fun saveApiKey(apiKey: String) {
        try {
            sharedPrefsManager.save(
                SharedPrefsManager.apiKey, apiKey
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }


    fun getApiKey(): String {
        return sharedPrefsManager.getStringData(SharedPrefsManager.apiKey) ?: ""
    }

}