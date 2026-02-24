package com.adham.weatherSample.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.adham.weatherSample.MainActivity
import com.adham.weatherSample.R
import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.extensions.kelvinToCelsiusInt

class WeatherNotificationService(
    private val context: Context,
) {
    init {
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT,
                ).apply {
                    description = CHANNEL_DESCRIPTION
                }

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun showNotification(
        weather: CurrentWeatherMapResponseModel,
        address: AddressModel,
    ) {
        val intent =
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        val temperature =
            weather.main.temp
                .kelvinToCelsiusInt()
                .toString()
        val notification =
            NotificationCompat
                .Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(context.getString(R.string.weatherInACity, address.name))
                .setContentText(
                    context.getString(
                        R.string.temperatureToday,
                        temperature,
                        weather.name,
                    ),
                ).setStyle(
                    NotificationCompat
                        .BigTextStyle()
                        .bigText(
                            context.getString(
                                R.string.temperatureToday,
                                temperature,
                                weather.name,
                            ),
                        ),
                ).setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

        with(NotificationManagerCompat.from(context)) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS,
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                notify(NOTIFICATION_ID, notification)
            }
        }
    }

    fun cancelNotifications() {
        with(NotificationManagerCompat.from(context)) {
            cancel(NOTIFICATION_ID)
        }
    }

    companion object {
        private const val CHANNEL_ID = "weather_forecast_notifications"
        private const val CHANNEL_NAME = "Weather forecast Notifications"
        private const val CHANNEL_DESCRIPTION = "Weather updates and alerts"
        private const val NOTIFICATION_ID = 1001
    }
}
