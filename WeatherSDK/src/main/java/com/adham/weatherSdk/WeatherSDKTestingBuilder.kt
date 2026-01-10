package com.adham.weatherSdk

import android.app.Application
import android.content.Context
import com.adham.weatherSdk.localStorages.SharedPrefsManagerImpl
import com.adham.weatherSdk.data.repositories.WeatherLocalRepositoryImpl

/**
 * Weather  s d k builder
 *
 */
object WeatherSDKTestingBuilder {

    /**
     * Initialize
     *
     * @param application
     * @return
     */
    fun initialize(
        application: Application
    ): WeatherSDKTestingBuilder {

        val localStorage = WeatherLocalRepositoryImpl(
            SharedPrefsManagerImpl(
                application.getSharedPreferences(
                    SharedPrefsManagerImpl.sharedPrefsUtilPrefix, //+ BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE
                )
            )
        )

        //reset the local storage
        localStorage.saveApiKey("")


        return this
    }

}