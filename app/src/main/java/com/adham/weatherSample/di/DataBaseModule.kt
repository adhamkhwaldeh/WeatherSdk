package com.adham.weatherSample.di


import com.adham.weatherSdk.WeatherSDK
import com.adham.weatherSample.helpers.AppConstantsHelper
import com.adham.weatherSample.orm.WeatherDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module


var dataBaseModule = module {
    single {
        WeatherDatabase.getDatabase(androidApplication())
    }
}