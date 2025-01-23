package com.adham.gini.weatherginisdk

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.adham.gini.weatherginisdk.data.states.WeatherSdkStatus
import com.adham.gini.weatherginisdk.di.generalModule
import com.adham.gini.weatherginisdk.di.localStorageModule
import com.adham.gini.weatherginisdk.di.networkModule
import com.adham.gini.weatherginisdk.di.repositoriesModule
import com.adham.gini.weatherginisdk.di.useCasesModule
import com.adham.gini.weatherginisdk.di.viewModelsModule
import com.adham.gini.weatherginisdk.localStorages.SharedPrefsManager
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.useCases.CurrentWeatherUseCase
import com.adham.gini.weatherginisdk.useCases.ForecastWeatherUseCase
import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.KoinScopeComponent
import org.koin.core.component.inject
import org.koin.core.context.startKoin
import org.koin.core.scope.Scope
import org.koin.dsl.module

object WeatherGiniSDKBuilder {
    val sdkStatus: MutableLiveData<WeatherSdkStatus> = MutableLiveData()

//    var sdkStatusListener: OnSdkStatusChangeListener? = null

//    Also we can use EventBus or broadcast receiver

    /**
     * TODO
     *
     * @param application
     * @param apiKey
     * @return
     */
    fun initialize(
        application: Application,
        apiKey: String
    ): WeatherGiniSDKBuilder {

        val sdkModule = module {
            single {
                val localStorage: WeatherGiniLocalRepository by inject()
                localStorage.saveApiKey(apiKey)
                this@WeatherGiniSDKBuilder
            }
        }

        startKoin {
            androidContext(application)
            modules(
                listOf(
                    generalModule,
                    localStorageModule,
                    networkModule,
                    repositoriesModule,
                    useCasesModule,
                    viewModelsModule,
                    sdkModule
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

//        localStorage.saveBaseUrl(baseUrl)
        localStorage.saveApiKey(apiKey)

        return this
    }

}