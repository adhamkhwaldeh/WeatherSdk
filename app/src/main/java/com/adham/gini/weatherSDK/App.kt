package com.adham.weatherSdk

import android.app.Application
import com.adham.weatherSdk.helpers.AppConstantsHelper

/**
 * App
 *
 * @constructor Create empty App
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        WeatherSDKBuilder.initialize(
            this,
//            ""
            AppConstantsHelper.apiKey
        )
    }
}