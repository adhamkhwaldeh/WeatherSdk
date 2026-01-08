package com.adham.weatherSdk

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import com.adham.weatherSdk.di.generalModule
import com.adham.weatherSdk.di.localStorageModule
import com.adham.weatherSdk.di.networkModule
import com.adham.weatherSdk.di.repositoriesModule
import com.adham.weatherSdk.di.useCasesModule
import com.adham.weatherSdk.di.viewModelsModule
import com.adham.weatherSdk.localStorages.SharedPrefsManager
import com.adham.weatherSdk.repositories.WeatherGiniLocalRepository
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Weather gini sdk builder
 *
 */
object WeatherSDKBuilder {

    val sdkStatus: MutableLiveData<WeatherSdkStatus> = MutableLiveData()

//    var sdkStatusListener: OnSdkStatusChangeListener? = null

//    Also we can use EventBus or broadcast receiver

    /**
     * Initialize
     *
     * @param application
     * @param apiKey
     * @return
     */
    fun initialize(
        application: Application,
        apiKey: String
    ): WeatherSDKBuilder {
//
//        val sdkModule = module {
//            single {
//                val localStorage: WeatherGiniLocalRepository by inject()
//                localStorage.saveApiKey(apiKey)
//                this@WeatherGiniSDKBuilder
//            }
//        }

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
//                    sdkModule
                )
            )
        }

        //I avoid to inject the local storage here because It will force the user to Use koin in his solution
        val localStorage = WeatherGiniLocalRepository(
            SharedPrefsManager(
                application.getSharedPreferences(
                    SharedPrefsManager.sharedPrefsUtilPrefix, //+ BuildConfig.APPLICATION_ID,
                    Context.MODE_PRIVATE
                )
            )
        )

        localStorage.saveApiKey(apiKey)

        return this
    }

}