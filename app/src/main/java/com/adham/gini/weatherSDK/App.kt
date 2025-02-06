package com.adham.gini.weatherSDK

import android.app.Application
import com.adham.gini.weatherSDK.helpers.AppConstantsHelper
import com.adham.gini.weatherginisdk.WeatherGiniSDKBuilder
import com.adham.gini.weatherginisdk.helpers.ConstantsHelpers

/**
 * App
 *
 * @constructor Create empty App
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        WeatherGiniSDKBuilder.initialize(
            this,
//            ""
            AppConstantsHelper.apiKey
        )
    }
}