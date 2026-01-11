package com.adham.weatherSample.di

import com.adham.weatherSample.viewModels.WeatherViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


var viewModelsModule = module {
    viewModel {
        WeatherViewModel(
            androidApplication(),
            get(),
        )
    }
}
