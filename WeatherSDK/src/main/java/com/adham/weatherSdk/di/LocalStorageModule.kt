package com.adham.weatherSdk.di
//
//import android.content.Context
//import com.adham.weatherSdk.localStorages.SharedPrefsManagerImpl
//import org.koin.android.ext.koin.androidApplication
//import org.koin.dsl.module
//
//val localStorageModule = module {
//    single {
//        SharedPrefsManagerImpl(
//            androidApplication().getSharedPreferences(
//                SharedPrefsManagerImpl.sharedPrefsUtilPrefix, //+ BuildConfig.APPLICATION_ID,
//                Context.MODE_PRIVATE
//            )
//        )
//    }
//}