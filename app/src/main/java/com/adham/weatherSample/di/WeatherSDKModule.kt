package com.adham.weatherSample.di

import com.adham.weatherSample.helpers.AppConstantsHelper
import com.adham.weatherSample.logging.ConsoleLogger
import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.settings.WeatherSDKOptions
import com.github.adhamkhwaldeh.commonsdk.logging.LogLevel
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var weatherSDKModule =
    module {
        single {
            WeatherSDK
                .Builder(
                    androidApplication(),
                    AppConstantsHelper.API_KEY,
                    AppConstantsHelper.API_WEATHER_MAP_KEY,
//                    "API_KEY",
                    "AIzaSyCC603JtJGcylfHzU2pu4auIPj-2WzhQxU",
//                    AppConstantsHelper.API_KEY_EXPIRED,
                ).setupOptions(
                    WeatherSDKOptions
                        .Builder(
                            //                            AppConstantsHelper.API_KEY_EXPIRED,
                            AppConstantsHelper.API_KEY,
                            AppConstantsHelper.API_WEATHER_MAP_KEY,
                            //                "API_KEY",
                            "AIzaSyCC603JtJGcylfHzU2pu4auIPj-2WzhQxU",
                        ).setLogLevel(LogLevel.DEBUG)
                        .setOverridable(false)
                        .setDebugMode(true)
                        .build(),
                ).addLogger(ConsoleLogger())
                .build()
        }
    }
