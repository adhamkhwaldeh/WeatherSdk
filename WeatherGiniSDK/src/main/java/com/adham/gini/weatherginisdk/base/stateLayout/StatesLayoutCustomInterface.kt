package com.adham.gini.weatherginisdk.base.stateLayout

import androidx.compose.runtime.Composable
import androidx.compose.ui.UiComposable

interface StatesLayoutCustomInterface {
    fun noContent(): (@Composable @UiComposable () -> Unit)? = null
    fun internalServerError(): (@Composable @UiComposable () -> Unit)? = null
    fun invalidData(): (@Composable @UiComposable () -> Unit)? = null
    fun notAuthorized(): (@Composable @UiComposable () -> Unit)? = null
    fun noInternetError(): (@Composable @UiComposable () -> Unit)? = null
    fun validationError(): (@Composable @UiComposable () -> Unit)? = null

}