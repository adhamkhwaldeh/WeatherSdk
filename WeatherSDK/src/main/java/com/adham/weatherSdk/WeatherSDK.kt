package com.adham.weatherSdk

import android.content.Context
import com.adham.weatherSdk.data.interfaces.OnSdkStatusChangeListener
import com.adham.weatherSdk.data.remote.dtos.weather.CurrentWeatherResponse
import com.adham.weatherSdk.data.remote.dtos.weather.ForecastResponse
import com.adham.weatherSdk.domain.models.AddressModel
import com.adham.weatherSdk.domain.models.CurrentWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.ForecastWeatherMapResponseModel
import com.adham.weatherSdk.domain.models.GeoByNameModel
import com.adham.weatherSdk.domain.models.PlaceModel
import com.adham.weatherSdk.domain.useCases.params.ForecastWeatherUseCaseParams
import com.adham.weatherSdk.domain.useCases.params.GeoByNameWeatherMapUseCaseParams
import com.adham.weatherSdk.domain.useCases.params.NameByGeoWeatherMapUseCaseParams
import com.adham.weatherSdk.exceptions.ApiKeyNotValidException
import com.adham.weatherSdk.providers.DataProvider
import com.adham.weatherSdk.providers.DomainProvider
import com.adham.weatherSdk.settings.WeatherSDKOptions
import com.github.adhamkhwaldeh.commonlibrary.base.states.BaseState
import com.github.adhamkhwaldeh.commonsdk.listeners.errors.ErrorListener
import com.github.adhamkhwaldeh.commonsdk.sdks.BaseSDKImpl
import com.google.android.libraries.places.api.Places
import kotlinx.coroutines.flow.Flow

/**
 * Weather sdk builder
 *
 */
@Suppress("TooManyFunctions")
class WeatherSDK private constructor(
    context: Context,
    sdkConfig: WeatherSDKOptions,
) : BaseSDKImpl<OnSdkStatusChangeListener, ErrorListener, WeatherSDKOptions>(context, sdkConfig) {
    init {
        if (sdkConfig.apiKey.isEmpty() || sdkConfig.weatherApiKey.isEmpty()) {
            notifyGlobalErrorListeners(ApiKeyNotValidException("Api Key is not valid"))
        } else {
//            val localStorage = DataProvider.provideWeatherLocalRepository(context)
//            localStorage.saveApiKey(sdkConfig.apiKey)

            val mapLocalStorage = DataProvider.provideWeatherMapLocalRepository(context)
            mapLocalStorage.saveApiKey(sdkConfig.weatherApiKey)

            if (!Places.isInitialized()) {
                Places.initializeWithNewPlacesApiEnabled(
                    context,
                    sdkConfig.placeApiKey,
                )
            }

            notifyListeners { it.onSdkInitialized() }
        }
    }

    /**
     * Builder for creating and configuring a `UserBehaviorCoreSDK` instance.
     * @param context The application context.
     */
    class Builder(
        context: Context,
        private val apiKey: String,
        private val weatherApiKey: String,
        private val placeApiKey: String,
    ) : BaseSDKImpl.Builder<Builder, OnSdkStatusChangeListener, ErrorListener, WeatherSDKOptions, WeatherSDK>(
            context,
        ) {
        /**
         * Builds and returns a configured instance of the UserBehaviorCoreSDK.
         *
         * @return A new instance of UserBehaviorCoreSDK.
         */
        override fun build(): WeatherSDK {
            val sdkConfig =
                sdkConfig ?: WeatherSDKOptions.Builder(apiKey, weatherApiKey, placeApiKey).build()
            val sdk = WeatherSDK(context, sdkConfig)

            if (customLoggers.isNotEmpty()) {
                sdk.updateLoggers(customLoggers)
            }

            return sdk
        }
    }

    suspend fun currentWeatherUseCase(params: String): Flow<BaseState<CurrentWeatherResponse>> {
        val useCase = DomainProvider.provideCurrentWeatherUseCase(context)
        return useCase.invoke(params)
    }

    suspend fun forecastWeatherUseCase(params: ForecastWeatherUseCaseParams): Flow<BaseState<ForecastResponse>> {
        val useCase = DomainProvider.provideForecastWeatherUseCase(context)
        return useCase.invoke(params)
    }

    suspend fun currentWeatherMapUseCase(params: AddressModel): Flow<BaseState<CurrentWeatherMapResponseModel>> {
        val useCase = DomainProvider.provideCurrentWeatherMapUseCase(context)
        return useCase.invoke(params)
    }

    suspend fun forecastWeatherUseCase(params: AddressModel): Flow<BaseState<ForecastWeatherMapResponseModel>> {
        val useCase = DomainProvider.provideCurrentWeatherMapForecastUseCase(context)
        return useCase.invoke(params)
    }

    @Suppress("MaxLineLength")
    suspend fun geoByNameWeatherMapUseCase(params: GeoByNameWeatherMapUseCaseParams): Flow<BaseState<Set<GeoByNameModel>>> {
        val useCase = DomainProvider.provideGeoByNameWeatherMapUseCase(context)
        return useCase.invoke(params)
    }

    @Suppress("MaxLineLength")
    suspend fun nameByGeoWeatherMapUseCas(params: NameByGeoWeatherMapUseCaseParams): Flow<BaseState<Set<GeoByNameModel>>> {
        val useCase =
            DomainProvider.provideNameByGeoWeatherMapUseCase(context)
        return useCase.invoke(params)
    }

    suspend fun allSavedAddresses(): Flow<List<AddressModel>> {
        val useCase = DomainProvider.provideGetAllSavedAddressesUseCase(context)
        return useCase.invoke(Unit)
    }

    suspend fun getDefaultAddressUseCase(): Flow<AddressModel?> {
        val useCase = DomainProvider.provideGetDefaultAddressUseCase(context)
        return useCase.invoke(Unit)
    }

    suspend fun insertOrUpdateAddressWithDefaultUseCase(addressModel: AddressModel): Flow<BaseState<AddressModel>> {
        val useCase = DomainProvider.provideInsertOrUpdateAddressWithDefaultUseCase(context)
        return useCase.invoke(addressModel)
    }

    suspend fun deleteAddressUseCase(addressModel: AddressModel): Flow<BaseState<Unit>> {
        val useCase = DomainProvider.provideDeleteAddressUseCase(context)
        return useCase.invoke(addressModel)
    }

    suspend fun placesSearchUseCase(query: String): Flow<BaseState<List<PlaceModel>>> {
        val useCase = DomainProvider.providePlacesSearchUseCase(context)
        return useCase.invoke(query)
    }

    suspend fun placesPlaceByIdUseCase(placeId: String): Flow<BaseState<GeoByNameModel>> {
        val useCase = DomainProvider.providePlacesPlaceByIdUseCase(context)
        return useCase.invoke(placeId)
    }
}
