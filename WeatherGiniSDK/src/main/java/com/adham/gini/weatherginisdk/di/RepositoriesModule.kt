package com.adham.gini.weatherginisdk.di

import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import org.koin.dsl.module


val repositoriesModule = module {

    single { WeatherGiniLocalRepository(get()) }

    single {
        WeatherGiniRepository(get())
    }

}