package com.adham.weatherSample.presentation.viewModels

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.viewModelScope
import com.adham.dvt.commonlibrary.base.BaseRefactorViewModel
import com.adham.weatherSample.domain.models.LanguageMode
import com.adham.weatherSample.domain.models.ThemeMode
import com.adham.weatherSample.domain.models.WeatherNotificationMode
import com.adham.weatherSample.preferences.PreferencesManager
import com.adham.weatherSample.presentation.states.SettingsState
import com.adham.weatherSample.services.NotificationSchedulerManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingsViewModel(
    application: Application,
    private val preferencesManager: PreferencesManager,
    private val notificationSchedulerManager: NotificationSchedulerManager,
) : BaseRefactorViewModel(application) {
    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            preferencesManager.settings.collect { preferences ->
                _uiState.value =
                    _uiState.value.copy(
                        themeMode = preferences.themeMode,
                        languageMode = preferences.languageMode,
                        weatherNotificationMode = preferences.weatherNotificationMode,
                    )
            }
        }
    }

    fun updateThemeMode(mode: ThemeMode) {
        viewModelScope.launch {
            preferencesManager.updateThemeMode(mode)
        }
    }

    fun updateLanguageMode(mode: LanguageMode) {
        viewModelScope.launch {
            preferencesManager.updateLanguageMode(mode)
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.forLanguageTags(mode.code),
            )
        }
    }

    fun updateNotificationMode(mode: WeatherNotificationMode) {
        viewModelScope.launch {
            preferencesManager.updateNotificationMode(mode)
            if (mode == WeatherNotificationMode.NEVER) {
                notificationSchedulerManager.cancelNotifications()
            } else {
                notificationSchedulerManager.scheduleNotifications(mode)
            }
        }
    }
}
