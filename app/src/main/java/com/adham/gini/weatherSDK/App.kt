package com.adham.gini.weatherSDK

import android.app.Application
import com.adham.gini.weatherginisdk.WeatherGiniSDKBuilder
import com.adham.gini.weatherginisdk.helpers.ConstantsHelpers

class App : Application() {

//    private val weatherGiniSDKBuilder by lazy { WeatherGiniSDKBuilder() }

    override fun onCreate() {
        super.onCreate()
        WeatherGiniSDKBuilder.initialize(
            this,
            ConstantsHelpers.apiKey
        )
    }
}