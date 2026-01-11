package com.adham.weatherSdk.localStorages

interface SharedPrefsManager {
    fun save(key: String, value: String)
    fun getStringData(key: String): String?
}
