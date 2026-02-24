package com.adham.weatherSample.di

import com.adham.weatherSample.preferences.PreferencesManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

var preferencesModule =
    module {
        single {
            PreferencesManager(
                androidApplication(),
            )
        }
    }
