package com.adham.weatherSdk

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import com.adham.weatherSdk.localStorages.SharedPrefsManagerImpl
import com.adham.weatherSdk.providers.DataProvider
import com.adham.weatherSdk.repositories.WeatherLocalRepositoryImpl
import com.adham.weatherSdk.settings.WeatherSDKOptions
import com.github.adhamkhwaldeh.commonsdk.BaseSDK
import com.github.adhamkhwaldeh.commonsdk.logging.ILogger


/**
 * Weather sdk builder
 *
 */
class WeatherSDK  private constructor(
    context: Context,
    sdkConfig: WeatherSDKOptions
) : BaseSDK<WeatherSDKOptions>(context,sdkConfig){

    /**
     * Builder for creating and configuring a `UserBehaviorCoreSDK` instance.
     * @param context The application context.
     */
    class Builder(private val context: Context,apiKey: String) {

        private var sdkConfig = WeatherSDKOptions.Builder(apiKey).build()

        private val customLoggers = mutableListOf<ILogger>()

        /**
         * Applies a custom configuration to the SDK. If not called, a default
         * configuration will be used.
         *
         * @param config The [UserBehaviorSDKConfig] instance.
         * @return The Builder instance for chaining.
         */
        fun withConfig(config: WeatherSDKOptions): Builder {
            this.sdkConfig = config
            return this
        }

        /**
         * Adds a custom logger implementation. Multiple loggers can be added.
         * If at least one custom logger is provided, the default logcat logger will be cleared.
         *
         * @param logger The custom logger to add.
         * @return The Builder instance for chaining.
         */
        fun addLogger(logger: ILogger): Builder {
            customLoggers.add(logger)
            return this
        }

        /**
         * Builds and returns a configured instance of the UserBehaviorCoreSDK.
         *
         * @return A new instance of UserBehaviorCoreSDK.
         */
        fun build(): WeatherSDK {
            val sdk = WeatherSDK(context, sdkConfig)

            val localStorage = DataProvider.provideWeatherLocalRepository(context)
            localStorage.saveApiKey(sdkConfig.apiKey)

            sdk.updateLoggers(customLoggers)

            return sdk
        }
    }

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
    ): WeatherSDK {

        return this
    }

}