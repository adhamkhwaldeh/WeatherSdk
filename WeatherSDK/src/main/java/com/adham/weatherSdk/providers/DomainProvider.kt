package com.adham.weatherSdk.providers

import android.content.Context
import com.adham.weatherSdk.domain.useCases.CurrentWeatherMapForecastUseCase
import com.adham.weatherSdk.domain.useCases.CurrentWeatherMapUseCase
import com.adham.weatherSdk.domain.useCases.CurrentWeatherUseCase
import com.adham.weatherSdk.domain.useCases.DeleteAddressUseCase
import com.adham.weatherSdk.domain.useCases.ForecastWeatherUseCase
import com.adham.weatherSdk.domain.useCases.GeoByNameWeatherMapUseCase
import com.adham.weatherSdk.domain.useCases.GetAllSavedAddressesUseCase
import com.adham.weatherSdk.domain.useCases.GetDefaultAddressUseCase
import com.adham.weatherSdk.domain.useCases.InsertOrUpdateAddressWithDefaultUseCase
import com.adham.weatherSdk.domain.useCases.NameByGeoWeatherMapUseCase
import com.adham.weatherSdk.domain.useCases.PlacesPlaceByIdUseCase
import com.adham.weatherSdk.domain.useCases.PlacesSearchUseCase

@Suppress("TooManyFunctions")
internal object DomainProvider {
    fun provideCurrentWeatherUseCase(context: Context) =
        CurrentWeatherUseCase(
            DataProvider.provideWeatherRepository(context),
            DataProvider.provideWeatherLocalRepository(context),
        )

    fun provideForecastWeatherUseCase(context: Context) =
        ForecastWeatherUseCase(
            DataProvider.provideWeatherRepository(context),
            DataProvider.provideWeatherLocalRepository(context),
        )

    fun provideCurrentWeatherMapUseCase(context: Context) =
        CurrentWeatherMapUseCase(
            DataProvider.provideWeatherMapRepository(context),
            DataProvider.provideWeatherMapLocalRepository(context),
        )

    fun provideCurrentWeatherMapForecastUseCase(context: Context) =
        CurrentWeatherMapForecastUseCase(
            DataProvider.provideWeatherMapRepository(context),
            DataProvider.provideWeatherMapLocalRepository(context),
        )

    fun provideGeoByNameWeatherMapUseCase(context: Context) =
        GeoByNameWeatherMapUseCase(
            DataProvider.provideWeatherMapRepository(context),
            DataProvider.provideWeatherMapLocalRepository(context),
        )

    fun provideNameByGeoWeatherMapUseCase(context: Context) =
        NameByGeoWeatherMapUseCase(
            DataProvider.provideWeatherMapRepository(context),
            DataProvider.provideWeatherMapLocalRepository(context),
        )

    fun provideGetAllSavedAddressesUseCase(context: Context) =
        GetAllSavedAddressesUseCase(
            DataProvider.provideAddressDao(context),
        )

    fun provideGetDefaultAddressUseCase(context: Context) =
        GetDefaultAddressUseCase(
            DataProvider.provideAddressDao(context),
        )

    fun provideInsertOrUpdateAddressWithDefaultUseCase(context: Context) =
        InsertOrUpdateAddressWithDefaultUseCase(
            DataProvider.provideAddressDao(context),
        )

    fun provideDeleteAddressUseCase(context: Context) =
        DeleteAddressUseCase(
            DataProvider.provideAddressDao(context),
        )

    fun providePlacesSearchUseCase(context: Context) =
        PlacesSearchUseCase(
            DataProvider.providePlacesRepository(context),
        )

    fun providePlacesPlaceByIdUseCase(context: Context) =
        PlacesPlaceByIdUseCase(
            DataProvider.providePlacesRepository(context),
        )
}
