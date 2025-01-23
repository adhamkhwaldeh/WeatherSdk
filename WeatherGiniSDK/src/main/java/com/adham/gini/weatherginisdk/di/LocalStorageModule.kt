package com.adham.gini.weatherginisdk.di

import android.content.Context
import com.adham.gini.weatherginisdk.localStorages.SharedPrefsManager
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