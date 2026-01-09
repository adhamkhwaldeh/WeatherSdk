package com.adham.weatherSdk.di

import com.adham.weatherSdk.viewModels.WeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


var viewModelsModule = module {
    viewModel {
        WeatherViewModel(
            androidApplication(),
            get(),
            get(),
        )
    }
}