package com.adham.weatherSample

import android.app.Application
import com.adham.weatherSample.di.dataBaseModule
import com.adham.weatherSample.di.viewModelsModule
//import com.adham.weatherSdk.di.viewModelsModule
import com.adham.weatherSample.di.weatherSDKModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * App
 *
 * @constructor Create empty App
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
//                    generalModule,
//                    localStorageModule,
//                    networkModule,
//                    repositoriesModule,
//                    useCasesModule,
                    dataBaseModule,
                    weatherSDKModule,
                    viewModelsModule,
                )
            )
        }

    }
}