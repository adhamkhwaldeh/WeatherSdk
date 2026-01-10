package com.adham.weatherSample.di

import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSample.helpers.AppConstantsHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


var weatherSDKModule = module {
    single {
        WeatherSDK.Builder(
            androidApplication(),
            AppConstantsHelper.apiKey
        ).build()
    }
}