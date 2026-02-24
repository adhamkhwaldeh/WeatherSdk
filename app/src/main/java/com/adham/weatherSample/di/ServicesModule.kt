package com.adham.weatherSample.di

import com.adham.weatherSample.services.NotificationSchedulerManager
import com.adham.weatherSample.services.WeatherNotificationService
import com.adham.weatherSample.services.WeatherNotificationWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.workerOf
import org.koin.dsl.module

var servicesModule =
    module {
        single {
            WeatherNotificationService(androidApplication())
        }
        single {
            NotificationSchedulerManager(androidApplication(), get())
        }
        workerOf(::WeatherNotificationWorker)
    }
