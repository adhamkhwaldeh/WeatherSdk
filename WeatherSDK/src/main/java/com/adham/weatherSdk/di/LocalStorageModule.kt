package com.adham.weatherSdk.di

import android.content.Context
import com.adham.weatherSdk.localStorages.SharedPrefsManager
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localStorageModule = module {
    single {
        SharedPrefsManager(
            androidApplication().getSharedPreferences(
                SharedPrefsManager.sharedPrefsUtilPrefix, //+ BuildConfig.APPLICATION_ID,
                Context.MODE_PRIVATE
            )
        )
    }
}