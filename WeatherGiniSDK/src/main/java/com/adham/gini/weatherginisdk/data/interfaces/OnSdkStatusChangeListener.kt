package com.adham.gini.weatherginisdk.data.interfaces

import java.lang.Exception

interface OnSdkStatusChangeListener {
    fun onFinish()
    fun onFinishWithError(exception: Exception?)
}