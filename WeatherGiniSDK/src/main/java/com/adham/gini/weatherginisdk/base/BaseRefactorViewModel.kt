package com.adham.gini.weatherginisdk.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import org.koin.core.component.KoinComponent

//import org.koin.core.KoinComponent

abstract class BaseRefactorViewModel(androidApplication: Application) :
    AndroidViewModel(androidApplication), KoinComponent {

}