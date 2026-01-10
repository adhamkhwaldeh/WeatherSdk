package com.adham.weatherSdk

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.data.interfaces.OnSdkStatusChangeListener
import com.adham.weatherSdk.data.params.ForecastWeatherUseCaseParams
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import com.adham.weatherSdk.providers.DataProvider
import com.adham.weatherSdk.providers.DomainProvider
import com.adham.weatherSdk.settings.WeatherSDKOptions
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonsdk.BaseSDK
import com.github.adhamkhwaldeh.commonsdk.logging.ILogger
import kotlinx.coroutines.flow.Flow


/**
 * Weather sdk builder
 *
 */
class WeatherSDK private constructor(
    context: Context,
    sdkConfig: WeatherSDKOptions
) : BaseSDK<OnSdkStatusChangeListener, WeatherSDKOptions>(context, sdkConfig) {

    val sdkStatus: MutableLiveData<WeatherSdkStatus> = MutableLiveData()

//    var sdkStatusListener: OnSdkStatusChangeListener? = null

    /**
     * Builder for creating and configuring a `UserBehaviorCoreSDK` instance.
     * @param context The application context.
     */
    class Builder(context: Context,private val apiKey: String) :
        BaseSDK.Builder<Builder, OnSdkStatusChangeListener, WeatherSDKOptions, WeatherSDK>(context) {

//
//        private val customLoggers = mutableListOf<ILogger>()

        /**
         * Applies a custom configuration to the SDK. If not called, a default
         * configuration will be used.
         *
         * @param config The [UserBehaviorSDKConfig] instance.
         * @return The Builder instance for chaining.
         */
//        fun withConfig(config: WeatherSDKOptions): Builder {
//            this.sdkConfig = config
//            return this
//        }

        /**
         * Adds a custom logger implementation. Multiple loggers can be added.
         * If at least one custom logger is provided, the default logcat logger will be cleared.
         *
         * @param logger The custom logger to add.
         * @return The Builder instance for chaining.
         */
//        fun addLogger(logger: ILogger): Builder {
//            customLoggers.add(logger)
//            return this
//        }

        /**
         * Builds and returns a configured instance of the UserBehaviorCoreSDK.
         *
         * @return A new instance of UserBehaviorCoreSDK.
         */
        override fun build(): WeatherSDK {

            val sdkConfig = sdkConfig ?: WeatherSDKOptions.Builder(apiKey).build()
            val sdk = WeatherSDK(context, sdkConfig)

            val localStorage = DataProvider.provideWeatherLocalRepository(context)
            localStorage.saveApiKey(sdkConfig.apiKey)

            sdk.updateLoggers(customLoggers)

            return sdk
        }

    }

    suspend fun currentWeatherUseCase(
        params: String
    ): Flow<BaseState<CurrentWeatherResponse>> {
        val useCase = DomainProvider.provideCurrentWeatherUseCase(context)
        return useCase.invoke(params)
    }

    suspend fun forecastWeatherUseCase(
        params: ForecastWeatherUseCaseParams
    ): Flow<BaseState<ForecastResponse>> {
        val useCase = DomainProvider.provideForecastWeatherUseCase(context)
        return useCase.invoke(params)
    }

}