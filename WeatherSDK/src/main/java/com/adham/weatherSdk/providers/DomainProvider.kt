package com.adham.weatherSdk.providers

import android.content.Context
import com.adham.weatherSdk.domain.useCases.CurrentWeatherUseCase
import com.adham.weatherSdk.domain.useCases.ForecastWeatherUseCase

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
}
