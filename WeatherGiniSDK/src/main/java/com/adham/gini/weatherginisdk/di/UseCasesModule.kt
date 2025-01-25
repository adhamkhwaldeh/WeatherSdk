package com.adham.gini.weatherginisdk.di

import com.adham.gini.weatherginisdk.useCases.CurrentWeatherUseCase
import com.adham.gini.weatherginisdk.useCases.ForecastWeatherUseCase
import org.koin.dsl.module

val useCasesModule = module {

    single { CurrentWeatherUseCase(get(),get(),get()) }

    single { ForecastWeatherUseCase(get(), get(), get()) }
}