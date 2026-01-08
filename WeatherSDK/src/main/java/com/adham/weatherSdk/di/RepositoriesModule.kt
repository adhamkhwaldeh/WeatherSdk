package com.adham.weatherSdk.di

import com.adham.weatherSdk.repositories.WeatherGiniLocalRepository
import com.adham.weatherSdk.repositories.WeatherGiniRepository
import org.koin.dsl.module


val repositoriesModule = module {

    single { WeatherGiniLocalRepository(get()) }

    single {
        WeatherGiniRepository(get(), get())
    }

}