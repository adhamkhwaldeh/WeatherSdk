package com.adham.weatherSample.services

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.adham.dvt.commonlibrary.base.states.BaseState
import com.adham.weatherSample.domain.models.UserSettingsModel
import com.adham.weatherSample.domain.models.WeatherNotificationMode
import com.adham.weatherSample.preferences.PreferencesManager
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.domain.models.AddressModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull

class WeatherNotificationWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val preferencesManager: PreferencesManager,
    private val notificationService: WeatherNotificationService,
    private val weatherSDK: WeatherSDK,
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result =
        try {
            val preferences = preferencesManager.settings.first()
            processWeatherNotification(preferences)
        } catch (_: Exception) {
            Result.failure()
        }

    @Suppress("ReturnCount")
    private suspend fun processWeatherNotification(preferences: UserSettingsModel): Result {
        if (preferences.weatherNotificationMode == WeatherNotificationMode.NEVER) {
            return Result.success()
        }

        val address: AddressModel? = weatherSDK.getDefaultAddressUseCase().firstOrNull()

        if (address != null) {
            weatherSDK.currentWeatherMapUseCase(address).collectLatest {
                if (it is BaseState.BaseStateLoadedSuccessfully) {
                    notificationService.showNotification(
                        weather = it.data,
                        address = address,
                    )
                }
            }
        }

        return Result.success()
    }
}
