package com.adham.weatherSample.services

import android.content.Context
import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.adham.weatherSample.domain.models.WeatherNotificationMode
import com.adham.weatherSample.extensions.interval
import java.util.concurrent.TimeUnit

class NotificationSchedulerManager(
    private val context: Context,
    private val notificationService: WeatherNotificationService,
) {
    fun scheduleNotifications(notificationMode: WeatherNotificationMode) {
        val workManager = WorkManager.getInstance(context)

        cancelNotifications()

        if (notificationMode == WeatherNotificationMode.NEVER || notificationMode.interval <= 0) {
            return
        }

        val constraints =
            Constraints
                .Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val workRequest =
            PeriodicWorkRequestBuilder<WeatherNotificationWorker>(
                notificationMode.interval,
                TimeUnit.MILLISECONDS,
            ).setConstraints(constraints)
                .build()

        workManager.enqueueUniquePeriodicWork(
            WEATHER_NOTIFICATION_WORK_NAME,
            ExistingPeriodicWorkPolicy.REPLACE,
            workRequest,
        )
    }

    fun cancelNotifications() {
        val workManager = WorkManager.getInstance(context)
        workManager.cancelUniqueWork(WEATHER_NOTIFICATION_WORK_NAME)
        notificationService.cancelNotifications()
    }

    companion object {
        private const val WEATHER_NOTIFICATION_WORK_NAME = "weather_notification_work"
    }
}
