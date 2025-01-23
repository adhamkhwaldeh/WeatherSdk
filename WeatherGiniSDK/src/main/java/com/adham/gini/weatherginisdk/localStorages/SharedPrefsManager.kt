package com.adham.gini.weatherginisdk.localStorages


import android.content.SharedPreferences


class SharedPrefsManager(private var sharedPreferences: SharedPreferences) {

    companion object {
        const val sharedPrefsUtilPrefix = "mySharedD_"

        const val baseURL = "baseURL"
        const val apiKey = "apiKey"

    }


    fun save(key: String, value: String) {

        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()

    }

    fun save(key: String, value: Int) {

        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()

    }

    fun save(key: String, `val`: Long) {

        val editor = sharedPreferences.edit()
        editor.putLong(key, `val`)
        editor.apply()

    }

    fun save(key: String, `val`: Boolean) {


        val editor = sharedPreferences.edit()
        editor.putBoolean(key, `val`)
        editor.apply()

    }

    @Suppress("unused")
    fun getBoolData(key: String): Boolean {
        return sharedPreferences.getBoolean(key, false)
    }

    @Suppress("unused")
    fun getBoolData(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun getStringData(key: String): String? {
        val data = sharedPreferences.getString(key, null)
        return if (data != null && data.trim { it <= ' ' }.isEmpty()) {

            null
        } else data

    }

    @Suppress("unused")
    fun getIntData(key: String): Int {
        return sharedPreferences.getInt(key, 1)

    }

    @Suppress("unused")
    fun getLongData(key: String): Long {
        return sharedPreferences.getLong(key, 1)

    }


}