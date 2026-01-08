package com.adham.weatherSdk

import android.app.Application
import android.content.Context
import com.adham.weatherSdk.di.generalModule
import com.adham.weatherSdk.di.localStorageModule
import com.adham.weatherSdk.di.networkModule
import com.adham.weatherSdk.di.repositoriesMockModule
import com.adham.weatherSdk.di.useCasesModule
import com.adham.weatherSdk.di.viewModelsModule
import com.adham.weatherSdk.localStorages.SharedPrefsManager
import com.adham.weatherSdk.repositories.WeatherGiniLocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Weather gini s d k builder
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

        startKoin {
            androidContext(application)
            modules(
                listOf(
                    generalModule,
                    localStorageModule,
                    networkModule,
                    repositoriesMockModule,
                    useCasesModule,
                    viewModelsModule,
                )
            )
        }

        val localStorage = WeatherGiniLocalRepository(
            SharedPrefsManager(
                application.getSharedPreferences(
                    SharedPrefsManager.sharedPrefsUtilPrefix, //+ BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE
                )
            )
        )

        //reset the local storage
        localStorage.saveApiKey("")


        return this
    }

}