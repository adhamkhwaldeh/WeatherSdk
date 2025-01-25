package com.adham.gini.weatherginisdk

import android.app.Application
import android.content.Context
import com.adham.gini.weatherginisdk.di.generalModule
import com.adham.gini.weatherginisdk.di.localStorageModule
import com.adham.gini.weatherginisdk.di.networkModule
import com.adham.gini.weatherginisdk.di.repositoriesMockModule
import com.adham.gini.weatherginisdk.di.useCasesModule
import com.adham.gini.weatherginisdk.di.viewModelsModule
import com.adham.gini.weatherginisdk.localStorages.SharedPrefsManager
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Weather gini s d k builder
 *
 */
object WeatherGiniSDKTestingBuilder {

    /**
     * Initialize
     *
     * @param application
     * @return
     */
    fun initialize(
        application: Application
    ): WeatherGiniSDKTestingBuilder {

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