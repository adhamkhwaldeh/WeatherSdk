package com.adham.weatherSample.di

import com.adham.weatherSample.viewModels.WeatherViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

var viewModelsModule =
    module {
        viewModelOf(::WeatherViewModel)
    }
