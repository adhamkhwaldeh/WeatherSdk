package com.adham.gini.weatherginisdk.di

import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import io.mockk.mockk
import org.koin.dsl.module


val repositoriesMockModule = module {

    single { WeatherGiniLocalRepository(get()) }

    single {
        mockk<WeatherGiniRepository>()
    }

}