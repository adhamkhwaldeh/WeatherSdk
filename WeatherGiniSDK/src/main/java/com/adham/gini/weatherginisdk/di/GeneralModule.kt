package com.adham.gini.weatherginisdk.di

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import org.koin.dsl.module


var generalModule = module {
    single { CoroutineScope(Dispatchers.IO + Job()) }
}