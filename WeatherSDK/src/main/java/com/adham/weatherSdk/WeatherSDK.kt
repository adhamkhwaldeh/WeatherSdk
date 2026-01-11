package com.adham.weatherSdk

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.adham.weatherSdk.data.dtos.CurrentWeatherResponse
import com.adham.weatherSdk.data.dtos.ForecastResponse
import com.adham.weatherSdk.data.interfaces.OnSdkStatusChangeListener
import com.adham.weatherSdk.data.params.ForecastWeatherUseCaseParams
import com.adham.weatherSdk.data.states.WeatherSdkStatus
import com.adham.weatherSdk.exceptions.ApiKeyNotValidException
import com.adham.weatherSdk.providers.DataProvider
import com.adham.weatherSdk.providers.DomainProvider
import com.adham.weatherSdk.settings.WeatherSDKOptions
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import com.github.adhamkhwaldeh.commonsdk.sdks.BaseSDKImpl
import kotlinx.coroutines.flow.Flow


/**
 * Weather sdk builder
 *
 */
class WeatherSDK private constructor(
    context: Context,
    sdkConfig: WeatherSDKOptions
) : BaseSDKImpl<OnSdkStatusChangeListener, ErrorListener, WeatherSDKOptions>(context, sdkConfig) {

    init {
        if (sdkConfig.apiKey.isEmpty()) {
            notifyGlobalErrorListeners(ApiKeyNotValidException("Api Key is not valid"))
        } else {
            val localStorage = DataProvider.provideWeatherLocalRepository(context)
            localStorage.saveApiKey(sdkConfig.apiKey)
        }
    }

    val sdkStatus: MutableLiveData<WeatherSdkStatus> = MutableLiveData()

    /**
     * Builder for creating and configuring a `UserBehaviorCoreSDK` instance.
     * @param context The application context.
     */
    class Builder(context: Context, private val apiKey: String) :
        BaseSDKImpl.Builder<Builder, OnSdkStatusChangeListener,ErrorListener, WeatherSDKOptions, WeatherSDK>(
            context
        ) {

        /**
         * Builds and returns a configured instance of the UserBehaviorCoreSDK.
         *
         * @return A new instance of UserBehaviorCoreSDK.
         */
        override fun build(): WeatherSDK {

            val sdkConfig = sdkConfig ?: WeatherSDKOptions.Builder(apiKey).build()
            val sdk = WeatherSDK(context, sdkConfig)

            if (customLoggers.isNotEmpty()) {
                sdk.updateLoggers(customLoggers)
            }

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
