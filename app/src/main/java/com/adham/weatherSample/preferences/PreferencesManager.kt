package com.adham.weatherSample.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.adham.weatherSample.domain.models.LanguageMode
import com.adham.weatherSample.domain.models.ThemeMode
import com.adham.weatherSample.domain.models.UserSettingsModel
import com.adham.weatherSample.domain.models.WeatherNotificationMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PreferencesManager(
    context: Context,
) {
    private val Context.dataStore: DataStore<Preferences>
        by preferencesDataStore(name = PreferenceKeys.PREFERENCES_DATA_NAME)

    private val dataStore = context.dataStore

    val settings: Flow<UserSettingsModel> =
        dataStore.data.map { preferences ->
            UserSettingsModel(
                languageMode =
                    try {
                        LanguageMode.valueOf(
                            preferences[PreferenceKeys.LANGUAGE_MODE_TAG.PrefKey]
                                ?: LanguageMode.ENGLISH.name,
                        )
                    } catch (_: Exception) {
                        LanguageMode.ENGLISH
                    },
                themeMode =
                    try {
                        ThemeMode.valueOf(
                            preferences[PreferenceKeys.THEME_MODE_TAG.PrefKey]
                                ?: ThemeMode.SYSTEM.name,
                        )
                    } catch (_: Exception) {
                        ThemeMode.SYSTEM
                    },
                weatherNotificationMode =
                    try {
                        WeatherNotificationMode.valueOf(
                            preferences[PreferenceKeys.NOTIFICATION_MODE_TAG.PrefKey]
                                ?: WeatherNotificationMode.NEVER.name,
                        )
                    } catch (_: Exception) {
                        WeatherNotificationMode.NEVER
                    },
            )
        }

    suspend fun updateThemeMode(mode: ThemeMode) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.THEME_MODE_TAG.PrefKey] = mode.name
        }
    }

    suspend fun updateLanguageMode(mode: LanguageMode) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.LANGUAGE_MODE_TAG.PrefKey] = mode.name
        }
    }

    suspend fun updateNotificationMode(mode: WeatherNotificationMode) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.NOTIFICATION_MODE_TAG.PrefKey] = mode.name
        }
    }
}
