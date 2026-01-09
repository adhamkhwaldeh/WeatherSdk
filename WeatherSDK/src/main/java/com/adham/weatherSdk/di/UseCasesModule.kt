package com.adham.weatherSdk.di

import com.adham.weatherSdk.useCases.CurrentWeatherOldUseCase
import com.adham.weatherSdk.useCases.ForecastWeatherOldUseCase
import org.koin.dsl.module

val useCasesModule = module {

    single { CurrentWeatherOldUseCase(get(),get(),get()) }

    single { ForecastWeatherOldUseCase(get(), get(), get()) }
}