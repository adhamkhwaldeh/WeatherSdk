package com.adham.weatherSdk.di

import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSdk.helpers.AppConstantsHelper
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


var weatherSDKModule = module {
    single {
        WeatherSDK.Builder(
            androidApplication(),
            AppConstantsHelper.apiKey
        )
    }
}