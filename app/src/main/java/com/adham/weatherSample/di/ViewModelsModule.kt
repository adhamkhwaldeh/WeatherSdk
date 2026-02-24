package com.adham.weatherSample.di

import com.adham.weatherSample.presentation.viewModels.AddressGeoViewModel
import com.adham.weatherSample.presentation.viewModels.AddressViewModel
import com.adham.weatherSample.presentation.viewModels.PlacesViewModel
import com.adham.weatherSample.presentation.viewModels.SettingsViewModel
import com.adham.weatherSample.presentation.viewModels.WeatherMapViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

var viewModelsModule =
    module {
        viewModelOf(::AddressViewModel)
        viewModelOf(::AddressGeoViewModel)
        viewModelOf(::WeatherMapViewModel)
        viewModelOf(::PlacesViewModel)
        viewModelOf(::SettingsViewModel)
    }
