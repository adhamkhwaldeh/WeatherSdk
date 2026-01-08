package com.adham.weatherSdk.di

import com.adham.weatherSdk.repositories.WeatherGiniLocalRepository
import com.adham.weatherSdk.repositories.WeatherGiniRepository
import io.mockk.mockk
import org.koin.dsl.module


val repositoriesMockModule = module {

    single { WeatherGiniLocalRepository(get()) }

    single {
        mockk<WeatherGiniRepository>()
    }

}