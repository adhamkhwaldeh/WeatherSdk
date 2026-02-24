package com.adham.weatherSample.preferences

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

object PreferenceKeys {
    const val PREFERENCES_DATA_NAME = "user_weather_preferences"
    const val THEME_MODE_TAG = "THEME_MODE_TAG"
    const val LANGUAGE_MODE_TAG = "LANGUAGE_MODE_TAG"

    const val NOTIFICATION_MODE_TAG = "NOTIFICATION_MODE_TAG"
}

val String.PrefKey: Preferences.Key<String>
    get() {
        return stringPreferencesKey(this)
    }
