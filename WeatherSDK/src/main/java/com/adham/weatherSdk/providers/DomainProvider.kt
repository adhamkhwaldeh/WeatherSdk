package com.adham.weatherSdk.providers

import android.content.Context
import com.adham.weatherSdk.domain.useCases.CurrentWeatherMapUseCase
import com.adham.weatherSdk.domain.useCases.CurrentWeatherUseCase
import com.adham.weatherSdk.domain.useCases.ForecastWeatherUseCase
import com.adham.weatherSdk.domain.useCases.GeoByNameWeatherMapUseCase
import com.adham.weatherSdk.domain.useCases.NameByGeoWeatherMapUseCase

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
}
