package com.adham.weatherSdk.domain.preferences

interface SharedPrefsManager {
    fun save(
        key: String,
        value: String,
    )

    fun getStringData(key: String): String?
}
