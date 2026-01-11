package com.adham.weatherSample.di

import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSample.helpers.AppConstantsHelper
import com.adham.weatherSample.logging.ConsoleLogger
import com.adham.weatherSdk.settings.WeatherSDKOptions
import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


var weatherSDKModule = module {
    single {
        WeatherSDK.Builder(
            androidApplication(),
            AppConstantsHelper.API_KEY,
        ).setupOptions(
            WeatherSDKOptions.Builder(AppConstantsHelper.API_KEY)
                .setLogLevel(LogLevel.DEBUG)
                .setOverridable(false)
                .setDebugMode(true).build()
        ).addLogger(ConsoleLogger()).build()
    }
}