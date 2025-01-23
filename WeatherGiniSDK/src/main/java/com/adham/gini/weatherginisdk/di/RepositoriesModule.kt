package com.adham.gini.weatherginisdk.di

import com.adham.gini.weatherginisdk.networking.WeatherService
import com.adham.gini.weatherginisdk.repositories.WeatherGiniLocalRepository
import com.adham.gini.weatherginisdk.repositories.WeatherGiniRepository
import org.koin.dsl.module
import retrofit2.Retrofit


val repositoriesModule = module {

    single { WeatherGiniLocalRepository(get()) }

    single { WeatherGiniRepository(get(),get()) }

}