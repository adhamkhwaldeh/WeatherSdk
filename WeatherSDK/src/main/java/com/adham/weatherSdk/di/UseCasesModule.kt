package com.adham.weatherSdk.di

import com.adham.weatherSdk.useCases.CurrentWeatherUseCase
import com.adham.weatherSdk.useCases.ForecastWeatherUseCase
import org.koin.dsl.module

val useCasesModule = module {

    single { CurrentWeatherUseCase(get(),get(),get()) }

    single { ForecastWeatherUseCase(get(), get(), get()) }
}