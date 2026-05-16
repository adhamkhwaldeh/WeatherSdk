package com.adham.weatherSample

import android.app.Application
import com.adham.weatherSample.di.preferencesModule
import com.adham.weatherSample.di.servicesModule
import com.adham.weatherSample.di.viewModelsModule
import com.adham.weatherSample.di.weatherSDKModule
import leakcanary.LeakCanary
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.startKoin

/**
 * App
 *
 * @constructor Create empty App
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        configureLeakCanary()
        startKoin {
            androidContext(this@App)
            workManagerFactory()
            modules(
                listOf(
                    preferencesModule,
                    weatherSDKModule,
                    servicesModule,
                    viewModelsModule,
                ),
            )
        }
    }

    private fun configureLeakCanary() {
        if (!BuildConfig.DEBUG || LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = true)
    }
}

